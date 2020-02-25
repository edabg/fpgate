/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function ($) {
    $(function () {
        $('.button-collapse').sideNav();
    }); // end of document ready
})(jQuery); // end of jQuery name space

$(document).ready(function () {
// TODO: Remove duplicated span.class.caret!
    $('select').material_select();
// the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('.modal-trigger').leanModal();
});

function cmdSelect(el) {
//    alert("Function:\n" + el.value);
    var params = {
        "PrinterStatus": ""
        , "PrintFiscalCheck":
                "UNS,XX000001-OP01-0000001\n" +
                "STG,Продукт от група B,B,0.12,0\n" +
                "STG,Продукт от група A,A,0.25,0\n" +
                "PFT,Примерен фиckaлeн текст\n" +
                "STL\n" +
                "TTL,Total:,CASH,2.00\n" +
                ""
        , "PrintFiscalCheck_Rev_":
                "UNS,XX000001-OP01-0000002\n" +
                "REV\n"+
                "RTA,RET\n"+
                "RDN,0000036\n"+
                "RDT,2019-02-13 12:17:04\n"+
                "RUS,DT000001-OP01-0000001\n"+
                "RFM,02278392\n"+
                "RRR,Неудоволетворен\n"+
                "STG,Продукт от група B,B,0.12,0\n" +
                "STG,Продукт от група A,A,0.25,0\n" +
                "PFT,Примерен фиckaлeн текст\n" +
                "STL\n" +
                "TTL,Total:\n" +
                ""
        , "PrintNonFiscalCheck":
                "PNT,@padl,,#\n" +
                "PNT,@padc,ДОКУМЕНТ\n" +
                "PNT,@padl,,#\n" +
                "PNT,Примерен текст\n" +
                "PNT,@padl,Ляво подравнен,.\n" +
                "PNT,@padr,Дясно подравнен,.\n" +
                "PNT,Следва етикет и стойност\\, етикета е ляво подравнен\\, стойността дясно\n" +
                "PNT,@lval,Етикет,Стойност\n" +
                "PNT,@padl,,#\n" +
                ""
        , "PrintDuplicateCheck": ""
        , "PrintDuplicateCheckByDocNum": "DocNum=00000000"
        , "PrintFiscalCheck_Inv_":
                "UNS,XX000001-OP01-0000001\n" +
                "INV\n" +
                "CUIC,818078106\n" +
                "CUIT,BULSTAT\n" +
                "CRCP,Драгомир Драганов\n" +
                "CBUY,Драго ТЕСТ ЕООД\n" +
                "CADR,Драгоман\n" +
                "CVAT,BG818078106\n" +
                "CSEL,Иван Иванов\n" +
                "STG,Продукт от група B,B,0.12,0\n" +
                "STG,Продукт от група A,A,0.25,0\n" +
                "PFT,Примерен фиckaлeн текст\n" +
                "STL\n" +
                "TTL,Total:,CASH,2.00\n" +
                ""
        , "PrintFiscalCheck_RevInv_":
                "UNS,XX000001-OP01-0000002\n" +
                "INV\n" +
                "CUIC,818078106\n" +
                "CUIT,BULSTAT\n" +
                "CRCP,Драгомир Драганов\n" +
                "CBUY,Драго ТЕСТ ЕООД\n" +
                "CADR,Драгоман\n" +
                "CVAT,BG818078106\n" +
                "CSEL,Иван Иванов\n" +
                "REV\n"+
                "RTA,RET\n"+
                "RDN,0000036\n"+
                "RDT,2019-02-13 12:17:04\n"+
                "RUS,DT000001-OP01-0000001\n"+
                "RFM,02278392\n"+
                "RRR,Неудоволетворен\n"+
                "RIN,0000000001\n"+
                "RID,2019-02-13\n"+
                "STG,Продукт от група B,B,0.12,0\n" +
                "STG,Продукт от група A,A,0.25,0\n" +
                "PFT,Примерен фиckaлeн текст\n" +
                "STL\n" +
                "TTL,Total:\n" +
                ""
        
        , "CurrentCheckInfo": ""
        , "LastFiscalRecordInfo": ""
        , "GetDiagnosticInfo": ""
        , "ReportDaily":
                "ReportType=X\n" +
                "#ReportType=Z\n" +
                ""
        , "ReportByDates":
                "ReportType=DETAIL\n" +
                "#ReportType=SHORT\n" +
                "FromDate=2015-07-01\n" +
                "ToDate=2015-07-03\n" +
                ""
        , "AbnormalComplete": ""
        , "GetDateTime": ""
        , "SetDateTime":
                "#DateTime=2019-02-14 10:01:02"
        , "CashInOut":
                "Amount=0\n" +
                ""
        , "CustomCommand":
                "Cmd=65\n" +
                "Args=0\n" +
                ""
        , "GetJournalInfo": ""
        , "GetJournal": 
                "#FromDate=2019-02-14\n"+
                "#ToDate=2019-02-14\n"+
                "#FromDoc=0000001\n"+
                "#ToDoc=0000001\n"
        , "GetDocInfo": 
                "#DocNum=0000001\n"
        , "ReadPaymentMethods": ""
        , "ReadDepartments": ""
        , "ReadTaxGroups": ""
        , "GetVersion": ""
        , "Test": ""
    }
    if (el.value in params)
        el.form.elements['Parameters'].value = params[el.value];
    else
        el.form.elements['Parameters'].value = "";
//alert("Parameters:\n" + el.form.elements['Parameters'].value);
    taRefresh(el.form, ['Parameters']);
}

function csvToTdv(csv) {
    var tdv = csv, token = '==token-escaped-comma==';
    tdv = tdv.replace(/([\\]\,)/g, token);
    tdv = tdv.replace(/[,]/g, '\t');
    tdv = tdv.replace(new RegExp(token, 'g'), ',');
    return tdv;
}

function taRefresh(f, ta) {
    try {
        Materialize.updateTextFields();
        $(ta).each(function (index, element) {
            $(f.elements[element]).trigger('keydown');
        });
    } catch (err) {
    }
}

var cmdClearForm = true;
var cmdClearFormTimeOut = 0;
function cmdClearFormCheck() {
    if (cmdClearForm) {
        cmdClearForm = false;
        clearTimeout(cmdClearFormTimeOut);
        cmdClearFormTimeOut = setTimeout(function () {
            cmdClearForm = true;
//            alert("cmdClearFormCheck: "+cmdClearFormTimeOut);
        }, 10000);
        return true;
    } else {
        return false;
    }
}

function cmdExecute(el) {
    var f = document.getElementById('printer_test');

    try {
        var cmdURL = window.location.protocol + '//' + window.location.host + '' + f.elements["Printer.URL"].value;
        var cmdID = f.elements["Printer.ID"].value;
        var cmdCommand = f.elements['Command'].value.replace(/_.*_/, '');
        var cmdArguments = csvToTdv(f.elements['Parameters'].value).split('\n');
//    alert(URL);
    } catch (err) {
        alert(err);
        return;
    }

    var ta = ["Result", "Errors", "Log"];
    if (cmdClearFormCheck()) {
        try {
            $(ta).each(function (index, element) {
                f.elements[element].value = '';
            });
        } catch (err) {
            alert(err);
            return;
        }
    }
    fpg = new FPGate({
        "URL": cmdURL // 'http://localhost:8182/print/'
        , "Printer": new FPGPrinter({
            "ID": cmdID
        })
    });

    Materialize.toast('Request sent!', 2000, "red s3");
    $('#spinner_execute').addClass("active");
    fpg.sendRequest(new FPGRequest({
        Command: cmdCommand
        , Arguments: cmdArguments
        , onRequestComplete: function (data, textStatus) {
            $(ta).each(function (index, element) {
                if ('' != f.elements[element].value)
                    f.elements[element].value += '------------------\n';
            });
            var resultData = null;
            if ('result' in data && data.result) {
                resultData = data.result;
            } else if ('error' in data && typeof data.error == 'object' && 'data' in data.error) {
                resultData = data.error.data;
            }
            try {
                for (var i in resultData.resultTable) {
                    f.elements['Result'].value += i + '=' + resultData.resultTable[i] + '\n';
                }
            } catch (err) {
            }
            try {
                for (var i in resultData.errors) {
                    f.elements['Errors'].value += resultData.errors[i] + '\n';
                }
            } catch (err) {
            }
            try {
                for (var i in resultData.log) {
                    f.elements['Log'].value += resultData.log[i] + '\n';
                }
            } catch (err) {
            }
            taRefresh(f, ta);
            Materialize.toast('Request complete!', 2000, "green")
            $('#spinner_execute').removeClass("active");
        }
        , onRequestError: function (textStatus, errorThrown) {
            Materialize.toast('Request error!\n' + textStatus + ':' + errorThrown, 5000, "orange");
            $('#spinner_execute').removeClass("active");
        }
    }));

}

