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
        "PrintFiscalCheck":
                "SON,Operator Name\n" +
                "STG,Product of tax group B,B,0.12,0\n" +
                "STG,Product of tax group A,A,0.25,0\n" +
                "PFT,Sample fiscal text\n" +
                "STL\n" +
                "TTL,Total:,CASH,2.00\n" +
                ""
        , "PrintNonFiscalCheck":
                "SON,Good Operator\n" +
                "PNT,@padl,,#\n" +
                "PNT,@padc,DOCUMENT\n" +
                "PNT,@padl,,#\n" +
                "PNT,Sample text\n" +
                "PNT,@padl,Left aligned,.\n" +
                "PNT,@padr,Right aligned,.\n" +
                "PNT,Next is label and value\\, label is left aligned and value to the right\n" +
                "PNT,@lval,Label,Value\n" +
                "PNT,@padl,,#\n" +
                ""
        , "PrintDuplicateCheck": ""
        , "LastFiscalRecordInfo": ""
        , "PrinterStatus": ""
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
                "#DateTime=2015-07-08 10:01:02"
        , "CashInOut":
                "Amount=0\n" +
                ""
        , "CustomCommand":
                "Cmd=65\n" +
                "Args=0\n" +
                ""
        , "GetJournalInfo": ""
        , "GetJournal": ""
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
        var cmdCommand = f.elements['Command'].value;
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

