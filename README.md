EDA FPGate Server
=================
EDA FPGate Server is application service which provide unified access to fiscal printers.
It is restful web service application which provide services with unified protocol for applications to work with various fiscal devices (mostly fiscal printers).
The main goal of FPGate is to separate common programming logic for work with fiscal devices from complexity to manage variety of fiscal devices.  

FPGate server is application which provide web API for access to unlimited number of predefined set of fiscal printers.
The server contains two main components:
1. Administration (HTML) interface for defining printers and users http://localhost:8182/admin/.
2. Application JSON protocol API interface for using printers http://localhost:8182/print/.

Current version of FPGate supports following fiscal printers:
DATECS FP3530,FP550,FP55,FP1000,FP300,FP60KL,FP2000KL, FP1000KL,FP700KL
Current version of FPGate supports following cash registers:
DATECS DP-05KL, DP-15KL,DP-25KL,DP-35KL,DP-45KL,DP-50KL,DP-50CKL,DP-55KL,MP-55 KL,DP-500PLUS KL


Web API
=======
API for access to printers is based on simple JSON application protocol over HTTP/HTTPS.
All API calls to services are in form of posting JSON Request object and receiving Response object as result of execution of request.

Request Object
---------------------------
Request Object contains following attributes:

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
ResultTable |Object     |Hash map result in form Key = Value
ErrorCode   |Integer    |Error code of last executed command (Depend of Command and specific printer implementation)
Messages    |Array      |List of messages in process of executing command
Errors      |Array      |List of errors and waring during execution of command
Log         |Array      |Detailed log of execution of command

The main considerations about successful execution of particular command will be places as result in ResultTable and depends of commend implementation.
For example if you request PrintFiscalCheck command (which contains subcommands), some of subcommand can return error. 
Printing fiscal check is atom command and always on success must complete with successful closing of fiscal check.
In that case you can check 'CloseStatus' field.

Web Appication Javascript Library
=================================
For simplifying implementation in web applications there is Javascript Library *fpg-client.js* which encapsulate protocol in useful objects.
Here is example request:

.. code-block:: javascript

    var fpg = new FPGate({
        URL: 'https//localhost:8183/print/'
        , Printer: new FPGPrinter({
            ID:'MyPrinter'
        })
    });
    var args = 
        'SON,Operator Name\n' +
        'STG,Product of tax group B,B,0.12,0\n' +
        'STG,Product of tax group A,A,0.25,0\n' +
        'PFT,Sample fiscal text\n' +
        'STL\n' +
        'TTL,Total:,CASH,2.00\n' +
        '';

    fpg.sendRequest(new FPGRequest({
        Command: 'PrintFiscalCheck'
        , Arguments : args.split('\\n')
        , onRequestComplete: function(data, textStatus) {
            try {
                try {
                    if ('errors' in data) {
                        for (var i in data.errors) 
                            alert('Error:'+data.errors[i]);
                    }	
                } catch (err) {
                    alert(err);
                }	
                if ('resultTable' in data) {
                    if ('CloseStatus' in data.resultTable && (data.resultTable.CloseStatus == '1')) {
                        // LastPrintDocNum
                        f['FPData[FPDocNum]'].value = ('LastPrintDocNum' in data.resultTable)?data.resultTable.LastPrintDocNum:'N/A';
                        f['FPData[SerialNum]'].value = ('LFRI_DocNum' in data.resultTable)?data.resultTable.LFRI_DocNum:'N/A';
                        // LFRI_DateTime
                        f['FPData[FPDocDate]'].value = ('LFRI_DateTime' in data.resultTable)?data.resultTable.LFRI_DateTime:'';
                        alert('SerialNum:'+f['FPData[SerialNum]'].value);
                        alert('FPDocNum:'+f['FPData[FPDocNum]'].value);
                        alert('FPDocDate:'+f['FPData[FPDocDate]'].value);
                        f.submit();
                    } else {
                        alert('Operation completed successfully!');
                    }	
                } else {
                    alert('Missing Result!');
                }
            } catch (err) {
                    alert(err);
            }	
        }
    }));

Implemented Commands
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
CMD        |Command,Params...                   | Call custom printer command, depending on printer model behavior will be different.

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
