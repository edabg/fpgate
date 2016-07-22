EDA FPGate Server
=================
EDA FPGate Server is application service which provide unified access to fiscal printers. 
The server is not complied to UnifiedPOS specification, but is made to be simple of use. 
It is restful web service application which provide unified protocol for access to various fiscal devices (fiscal printers and cash registers).
The main goal of FPGate is to separate common programming logic for work with fiscal devices from complexity to manage variety of fiscal devices.  


FPGate server is application which provide web API for access to unlimited number of predefined set of fiscal printers.
The server contains two main components:

1.Administration (HTML) interface for defining printers and users http://localhost:8182/admin/

2.Application JSON protocol API interface for using printers http://localhost:8182/print/

Current version of FPGate supports following fiscal printers:

DATECS FP3530,FP550,FP55,FP1000,FP300,FP60KL,FP2000KL, FP1000KL,FP700KL,FP705KL

Current version of FPGate supports following cash registers:

DATECS DP-05KL, DP-15KL,DP-25KL,DP-35KL,DP-45KL,DP-50KL,DP-50CKL,DP-55KL,MP-55 KL,DP-500PLUS KL


Requirements
============
- Java Runtime Environment 8.0+
- Drivers for implemented fiscal printers. 
For DATECS models [FP3530 COM Server](http://www.datecs.bg/en/downloads/zip?id=FP3530_COM_Server_SETUP_2016_04_06v2_0_0_845.zip) v2.0.0/845 or newer is required.

Installation
============
1. Download latest release of FPGateSrvInstall.jar from [Github](https://github.com/edabg/fpgate/releases).
2. Start FPGateSrvInstall.jar and complete the process.
3. Install printer drivers/libraries.
4. Start FPGate Server with $INSTALL_PATH\bin\FPGateSrv.cmd 
5. Configure server from Control panel and setup user administrator.
6. Launch browser and go to FPGate server administration (for example: http://localhost:8182/admin/)
7. Define printers and users.
8. Defined printers are ready to be used from applications (see examples bellow).

Web API
=======
API for access to printers is based on simple JSON-RPC (http://www.jsonrpc.org/specification) application protocol over HTTP/HTTPS.
All API calls to services are in form of posting JSON Request object and receiving Response object as result of execution of request.

Request Object
---------------------------
Request Object contains following attributes:

Attribute  |Type   |Description
-----------|-------|-------------------------
jsonrpc    |String |A String specifying the version of the JSON-RPC protocol. MUST be exactly "2.0".
method     |String |A String containing the name of the method/printer command to be invoked. Method names that begin with the word rpc followed by a period character (U+002E or ASCII 46) are reserved for rpc-internal methods and extensions and MUST NOT be used for anything else.
params     |Object |A Structured value that holds the parameter values to be used during the invocation of the method. 
id         |String |An identifier established by the Client that MUST contain a String, Number, or NULL value if included.

'params' object in request contain following attributes:

Attribute  |Type   |Description
-----------|-------|-------------------------
Printer    |Object |with following attributes
.ID        |String |Unique ID of printer
.Model     |String |Optional name of Printer Model Class (in case of using printer not defined on server)
.Params    |Object |Optional Hash map with printer parameters in form of pairs parameter_name = value 
Command    |String |Name of Printer Command
Arguments  |Array  |Array of strings with arguments of command


Response Object
---------------

Response Object contains following attributes:

 Attribute  |Type       |Description
------------|-----------|------------------------------------
jsonrpc     |String     |A String specifying the version of the JSON-RPC protocol. MUST be exactly "2.0".
result      |Object     |This member exists and is object in case of success. This member doesn't exists in case of error.
error       |Object     |This member exists in case of error. When error exists 'result' doesn't exist.
Errors      |Array      |List of errors and waring during execution of command
id          |String     |It contains the same as the value of the id member in the Request Object.


In case of success 'result' object contains following attributes:

 Attribute  |Type       |Description
------------|-----------|------------------------------------
ResultTable |Object     |Hash map result in form Key = Value
ErrorCode   |Integer    |Error code of last executed command (Depend of Command and specific printer implementation)
Messages    |Array      |List of messages in process of executing command
Errors      |Array      |List of errors and waring during execution of command
Log         |Array      |Detailed log of execution of command

In case of error 'error' object contains following attributes:

 Attribute  |Type       |Description
------------|-----------|------------------------------------
code        |Number     |A Number that indicates the error type that occurred.
message     |String     |A String providing a short description of the error.
data        |Object     |An object with same structure as 'result'. This contains particular information to the the point of error is occurred.


Web Appication Javascript Library
=================================
For simplifying implementation in web applications there is Javascript Library *fpg-client.js* which encapsulate protocol in useful objects.
Here is example request:

```javascript

    var fpg = new FPGate({
        URL: 'https//localhost:8183/print/'
        , Printer: new FPGPrinter({
            ID:'MyPrinter'
        })
    });
    var args = 
        'SON\tOperator Name\n' +
        'STG\tProduct of tax group B\tB\t0.12\t0\n' +
        'STG\tProduct of tax group A\tA\t0.25\t0\n' +
        'PFT\tSample fiscal text\n' +
        'STL\n' +
        'TTL\tTotal:\tCASH\t2.00\n' +
        '';

    fpg.sendRequest(new FPGRequest({
        Command: 'PrintFiscalCheck'
        , Arguments : args.split('\\n')
        , onRequestComplete: function(data, textStatus) {
            try {
                var resultData = null;
                if ('result' in data && data.result) {
                    resultData = data.result;
                } else if ('error' in data && typeof data.error == 'object' && 'data' in data.error) {
                    resultData = data.error.data;
                }
                if ('result' in data && data.result) {
                    var resultData = data.result.resultTable;
                    // LastPrintDocNum
                    f['FPData[FPDocNum]'].value = ('LastPrintDocNum' in resultData)?resultData.LastPrintDocNum:'N/A';
                    f['FPData[SerialNum]'].value = ('LFRI_DocNum' in resultData)?resultData.LFRI_DocNum:'N/A';
                    // LFRI_DateTime
                    f['FPData[FPDocDate]'].value = ('LFRI_DateTime' in resultData)?resultData.LFRI_DateTime:'';
                    alert('SerialNum:'+f['FPData[SerialNum]'].value);
                    alert('FPDocNum:'+f['FPData[FPDocNum]'].value);
                    alert('FPDocDate:'+f['FPData[FPDocDate]'].value);
                    f.submit();
                } else if ('error' in data && data.error) {
                    for (var i in data.error.data.errors) {
                            f.elements['Errors'].value += data.error.data.errors[i]+'\n';
                    }
                } else {
                    alert('Missing Result!');
                }
            } catch (err) {
                    alert(err);
            }	
        }
    }));
```

Implemented Methods
====================
FPGate Server supports following list of commands.

PrintFiscalCheck and PrintNonFiscalCheck
----------------------------------------
This command requesting printing of fiscal/non-fiscal check and accept as Arguments list of subcommands which generate content of fiscal check.
Every subcommand is string in the following format:
*subcommand*[\tParam...]
Where subcommand can be:

Subcommand | Parameters                         | Description
-----------|------------------------------------|-----------------------
SON        |OperatorName                        | Set operator name
PFT        |Text                                | Print Fiscal Text 
PNT        |Text                                | Print Nonfiscal Text 
PLF        |RowCount                            | Paper Line Feed
STG        |Text,TaxCode,Price,Percent,Quantity | Register Sell by Tax Group. Percent and Quantity are optional. Tax Code is Tax Group Abbreviation A,B,C...
SDP        |Text,DepCode,Price,Percent,Quantity | Register Sell by Department. Percent and Quantity are optional. Department Code is programmed code of department.
STL        |ToPrint,ToDisplay,Percent           | Calculate subtotal. Parameters are optional.
TTL        |Text,PaymentTypeAbbr,Amount         | Calc Total and prints the Text. PaymentTypeAbbr can be CASH,CREDIT,CHECK,DEBIT_CARD,CUSTOM1,CUSTOM2,CUSTOM3,CUSTOM4. Amount is Sum paid by customer.
CMD        |Command,Params...                   | Call custom printer command. Depending on printer model behavior will be different.

PFT and PNT supports some simple formatting syntax in the following from.
@padl\tText[\tPaddingSymbol] - Align text to the left and pad to the whole width with padding symbol (by default is space)
@padr\tText[\tPaddingSymbol] - Align text to the right and pad to the whole width with padding symbol (by default is space)
@padc\tText[\tPaddingSymbol] - Align text to the center and pad to the whole width with padding symbol (by default is space)
@lval\tLabel\tValue - Align Label And Value.

PrintDuplicateCheck
-------------------
Prints duplicate of last printed fiscal check. Can be executed only once.
Only one duplicate check can be printed.

LastFiscalRecordInfo
--------------------
Requests last fiscal record info.

ReportDaily
-----------
Request daily report. The commend accepts `ReportType` argument that selects desired report.
`ReportType=X` Requests X Report
`ReportType=Z` Requests Z Report

ReportByDates
-------------
Prints report by dates. The command accepts following arguments:
`ReportType=DETAIL` Detailed report
`ReportType=SHORT` Short report
`FromDate=2015-07-01` From date in format yyyy-mm-dd
`ToDate=2015-07-03` To date in format yyyy-mm-dd

GetDateTime
-----------
Get printer date and time.

SetDateTime
-----------
Set printer date and time. The command accepts optional argument `DateTime` where date and time is in following format 'yyyy-mm-dd HH:nn:ss'.
If `DateTime` argument is omitted the current date and time of system will be used.

PrinterStatus
-------------
Get printer status information. The result is dependent of printer model.

GetDiagnosticInfo
-----------------
Get printer diagnostic information. The result is dependent of printer model.

GetJournalInfo
--------------
Request journal information and return it. Available only if printer support it.

GetJournal
----------
Request printer journal return it. Available only if printer support it.

CashInOut
----------
Register `cash in` and `cash out` to the cash register. Accepts parameter `Amount` which set cash value. The positive `Amount` is  `cash in`, negative `Amount` is `cash out`, zero `Amount` prints current cash.

CustomCommand
-------------
Sends raw command to printer. The command accepts following arguments.
`Cmd=CmdCode` and `Args=arguments`.

AbnormalComplete
----------------
Request printer to cancel all pending operations and returns it in ready state.

Test
----
Request testoperation on printer and returns result.
Result depends on Printer Support class implementation. Mostly get diagnostic information is used.
