/* 
 * Copyright (C) 2015 EDA Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Create Fiscal printer instance
 *
 * @param   options An object describing printer options.
 *                  <code>ID</code> Printer ID
 *                  <code>Model</code> Model name
 *                  <code>Params</code> Printer parameters. 
 *                  List of available parameters is Model specific.
 *                  Example:
 *                  <code>
 *                  var fp = new FPGPrinter({
 *                      'ID':'FP2000KL'
 *                      , 'Params':{
 *                          'OperatorCode': '1'
 *                          , 'OperatorPass': '0000'
 *                      }
 *                  });
 *                  </code>
 *                  
 * @returns {FPGPrinter}
 */
function FPGPrinter(options) {
    this.ID = '';
    this.Model = '';
    this.Params = {};

    this.setParam = function (name, value) {
            this.Params[name] = value;	
    };

    if (typeof options === 'object') {
        if ('ID' in options)
           this.ID = options.ID;
        if ('Model' in options)
           this.Model = options.Model;
        if ('Params' in options && typeof options.Params === 'object')
           this.Params = options.Params;
    }

}

/**
 * 
 * @param   options Request object contains following properties.
 *                  <code>Printer</code> Optional {@link FPGPrinter} Object. 
 *                  If printer is not given the {@link FPGate} printer is used.
 *                  <code>Command</code> The name of command (required)
 *                  <code>Arguments</code> Arguments of command as string array. Content depends on type fo command.
 *                  <code>onRequestComplete(data, textStatus)</code> Callback function on request success copletition.
 *                  <code>onRequestError(textStatus, errorThrown)</code> Callback function on request execution error.
 *                  
 * @returns {FPGRequest}
 */
function FPGRequest(options) {
    this.Printer = null;
    this.Command = '';
    this.Arguments = [];
    this.RequestId = '';
    this.onRequestComplete = null;
    this.onRequestError = null;

    this.setPrinter = function (p) {
            this.Printer = p;
    };

    this.clearArguemnts = function () {
            this.Arguments = [];
    };

    this.addArguments = function (args) {
        var a,k,v;
        for (var i in args) {
            a = args[i];
            if (typeof a === 'object' || typeof a === 'array') {
                k = null;
                v = '';
                for(var j in a) {
                    if (k === null) 
                        k = a[j];
                    else
                        v = v+a[j];
                }
                this.addArgument(k,v);
            } else
                this.addArgument(a);
        }
    };
    
    this.addArgument = function (aname, aval) {
            if (typeof aval === 'undefined')
                    this.Arguments.push(aname);
            else		
                    this.Arguments.push(aname+'='+aval);
    };

    this.toJSON = function () {
      var req = {};
      req.method = this.Command;
      req.params = {};
      req.params.arguments = this.Arguments;
      req.params.printer = {};
      req.params.printer.ID = this.Printer.ID;
      req.params.printer.Model = this.Printer.Model;
      req.params.printer.Params = this.Printer.Params;
      req.id = this.RequestId;
//      alert($.toJSON(req));
      return $.toJSON(req);
    };

    if (typeof options === 'object') {
        if ('Printer' in options)
           this.Printer = options.Printer;
        if ('Command' in options)
           this.Command = options.Command;
        if ('Arguments' in options && 
            (typeof options.Arguments === 'object' || typeof options.Arguments === 'array')
           ) 
            this.addArguments(options.Arguments);
        if ('RequestId' in options)
            this.RequestId = options.RequestId;
        if ('onRequestComplete' in options)
            this.onRequestComplete = options.onRequestComplete;
        if ('onRequestError' in options)
            this.onRequestError = options.onRequestError;
    }
}

/**
 * Fiscal Printer Gateway communication object
 * 
 * @param  An options object sets parameters of Gateway
 *          <code>URL</code> URL of Fiscal Printer Gateway Server (http://localhost:8182/print)
 *          <code>Printer</code> {@link FPGPrinter} optional object. Can be given on request.
 *          <code>onRequestComplete(request, data, textStatus)</code> Callback function on request success copletition.
 *          <code>onRequestError(request, textStatus, errorThrown)</code> Callback function on request execution error.
 *          Example:
 *          <code>
 *    fpg = new FPGate({
 *        URL: 'http://localhost:8182/print/'
 *         , Printer: new FPGPrinter({
 *            ID:'FP2000KL'
 *            , Model:''
 *        })
 *    });
 *          
 *    fpg.sendRequest(new FPGRequest({
 *        Command: 'PrintFiscalCheck'
 *        , Arguments : [
 *          'STG\tAn Stock of TaxGroup B\tB\t0.12\t0'   // Sell by tax group
 *          , 'STG\tAn Stock of TaxGroup A\tB\t0.15\t0' // Sell by tax group
 *          , 'PFT\tSome fiscal text'
 *          , 'STL' // Subtotal
 *          , 'TTL\tTotal of check\tCASH\t20' // Total
 *        ]
 *        , onRequestComplete: function(data, textStatus) {
 *            // Result of command will be placed in form element
 *            try {
 *                f.elements['Result'].value = '';
 *                for (var i in data.resultTable) {
 *                        f.elements['Result'].value += i+'='+data.resultTable[i]+'\n';
 *                }
 *            } catch (err) {
 *            }	
 *            // List of errors
 *            try {
 *                f.elements['Errors'].value = '';
 *                for (var i in data.errors) {
 *                        f.elements['Errors'].value += data.errors[i]+'\n';
 *                }
 *            } catch (err) {
 *            }	
 *            // Execution log
 *            try {
 *                f.elements['Log'].value = '';
 *                for (var i in data.log) {
 *                        f.elements['Log'].value += data.log[i]+'\n';
 *                }
 *            } catch (err) {
 *            }	
 *        }
 *    }));
 *          
 *          </code>
 * @returns {FPGate}
 */
function FPGate(options) {
	this.Printer = new FPGPrinter();
	this.URL = ''; 
	this.Result = null;
	this.Error = null;
        this.onRequestComplete = null;
//	this.onRequestComplete = function(request, data, textStatus) {
//		alert('Request complete with status:'+textStatus);
//	}; 
        this.onRequestError = null;
//	this.onRequestError = function (request, textStatus, errorThrown) {
//		alert('Request execution error: '+textStatus+':'+errorThrown);
//	};
        
        /**
         * Sends request to the FPGate Server.
         * On success resul is returnen in <code>Result</code> property 
         * or as <code>data</code> parameter of onRequestComplete callback.
         * @param request {@link FPGRequest} object to be send.
         * @returns {undefined}
         */
	this.sendRequest = function (request) {
		if (!request.Printer) request.Printer = this.Printer;
		this.Result = null;
		this.Error = null;
                var thisgate = this;
		$.ajax({
			type: 'POST',
			url: thisgate.URL,
			data: request.toJSON(),
			dataType: 'json',
			contentType : 'application/json',
//                        headers: {
//                          "Authorization": "Basic " + btoa(thisgate.authUser + ":" + thisgate.authPass)
//                        },			crossDomain : true,
			success: function(data, textStatus, jqXHR){
	//			alert(JSON.stringify(data));
                            if ('result' in data && data.result) {
                                thisgate.Result = data.result;
                            } else if ('error' in data && typeof data.error == 'object' && 'data' in data.error) {
                                thisgate.Result = data.error.data;
                                thisgate.Error = data.error;
                            }
                            if (thisgate.onRequestComplete)
                                thisgate.onRequestComplete(request, data, textStatus);
                            if (request.onRequestComplete)
                                request.onRequestComplete(data, textStatus);
			},
			error: function (jqXHR, textStatus, errorThrown) {
                            if (thisgate.onRequestError)
                                thisgate.onRequestError(request, textStatus, errorThrown);
                            if (request.onRequestError)
                                request.onRequestError(textStatus, errorThrown);
			}
		});	
	};

        if (typeof options === 'object') {
            if ('Printer' in options && typeof options.Printer === 'object')
                this.Printer = options.Printer;
            if ('URL' in options)
                this.URL = options.URL;
            if ('onRequestComplete' in options)
                this.onRequestComplete = options.onRequestComplete;
            if ('onRequestError' in options)
                this.onRequestError = options.onRequestError;
        }
}

