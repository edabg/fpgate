/**
 * JacobGen generated file --- do not edit
 *
 * (http://www.sourceforge.net/projects/jacob-project */
package org.datecs.fpcom;

import com.jacob.com.*;

public class ICS_BGR_FMP350_KL extends Dispatch {

	public static final String componentName = "FP3530.ICS_BGR_FMP350_KL";

	public ICS_BGR_FMP350_KL() {
		super(componentName);
	}

	/**
	* This constructor is used instead of a case operation to
	* turn a Dispatch object into a wider object - it must exist
	* in every wrapper class whose instances may be returned from
	* method calls wrapped in VT_DISPATCH Variants.
	*/
	public ICS_BGR_FMP350_KL(Dispatch d) {
		// take over the IDispatch pointer
		m_pDispatch = d.m_pDispatch;
		// null out the input's pointer
		d.m_pDispatch = 0;
	}

	public ICS_BGR_FMP350_KL(String compName) {
		super(compName);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_GeneralType_1() {
		return Dispatch.get(this, "eSBit_GeneralType_1").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_GeneralType_2() {
		return Dispatch.get(this, "eSBit_GeneralType_2").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Command_SyntaxError() {
		return Dispatch.get(this, "eSBit_Command_SyntaxError").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Command_InvalidCode() {
		return Dispatch.get(this, "eSBit_Command_InvalidCode").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Command_NotPermited() {
		return Dispatch.get(this, "eSBit_Command_NotPermited").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Command_Overflow() {
		return Dispatch.get(this, "eSBit_Command_Overflow").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Full_EJournal() {
		return Dispatch.get(this, "eSBit_Full_EJournal").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_Full_FiscalMemory() {
		return Dispatch.get(this, "eSBit_Full_FiscalMemory").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_OutOf_Paper() {
		return Dispatch.get(this, "eSBit_OutOf_Paper").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean geteSBit_WriteError() {
		return Dispatch.get(this, "eSBit_WriteError").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_OpenedReceipt_Fiscal() {
		return Dispatch.get(this, "iSBit_OpenedReceipt_Fiscal").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_OpenedReceipt_NonFiscal() {
		return Dispatch.get(this, "iSBit_OpenedReceipt_NonFiscal").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_NearlyFull_EJournal() {
		return Dispatch.get(this, "iSBit_NearlyFull_EJournal").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_NearlyFull_FiscalMemory() {
		return Dispatch.get(this, "iSBit_NearlyFull_FiscalMemory").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_Setted_SerialNumber() {
		return Dispatch.get(this, "iSBit_Setted_SerialNumber").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_Setted_TaxNumber() {
		return Dispatch.get(this, "iSBit_Setted_TaxNumber").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_Setted_TaxRates() {
		return Dispatch.get(this, "iSBit_Setted_TaxRates").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_Formatted_FiscalMemory() {
		return Dispatch.get(this, "iSBit_Formatted_FiscalMemory").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getiSBit_DeviceIsFiscalized() {
		return Dispatch.get(this, "iSBit_DeviceIsFiscalized").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getdebug_Mode() {
		return Dispatch.get(this, "debug_Mode").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getdevice_Model() {
		return Dispatch.get(this, "device_Model").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getfirmware_Version() {
		return Dispatch.get(this, "firmware_Version").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getfirmware_Revision() {
		return Dispatch.get(this, "firmware_Revision").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getfirmware_Date() {
		return Dispatch.get(this, "firmware_Date").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getfirmware_CheckSum() {
		return Dispatch.get(this, "firmware_CheckSum").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getnum_DeviceSerialNumber() {
		return Dispatch.get(this, "num_DeviceSerialNumber").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getnum_FiscalMemoryNumber() {
		return Dispatch.get(this, "num_FiscalMemoryNumber").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getcountry_Code() {
		return Dispatch.get(this, "country_Code").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getcountry_Name() {
		return Dispatch.get(this, "country_Name").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getcodePage_BaseValue() {
		return Dispatch.get(this, "codePage_BaseValue").getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getcodePage_Custom() {
		return Dispatch.get(this, "codePage_Custom").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getcodePage_CustomCode() {
		return Dispatch.get(this, "codePage_CustomCode").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getcount_TaxRates() {
		return Dispatch.get(this, "count_TaxRates").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_Amount_BeforeZReport() {
		return Dispatch.get(this, "max_Amount_BeforeZReport").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getmax_SalesCount_BeforeZReport() {
		return Dispatch.get(this, "max_SalesCount_BeforeZReport").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getmax_FiscalMemoryRecords() {
		return Dispatch.get(this, "max_FiscalMemoryRecords").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_SinglePrice_ForItem() {
		return Dispatch.get(this, "max_SinglePrice_ForItem").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_Quantity_ForItem() {
		return Dispatch.get(this, "max_Quantity_ForItem").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_SinglePrice_ForSell() {
		return Dispatch.get(this, "max_SinglePrice_ForSell").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_Quantity_ForSell() {
		return Dispatch.get(this, "max_Quantity_ForSell").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_RowPrice_ForSell() {
		return Dispatch.get(this, "max_RowPrice_ForSell").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_AmountAdjustment_ForSell() {
		return Dispatch.get(this, "max_AmountAdjustment_ForSell").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_PercentAdjustment_ForSell() {
		return Dispatch.get(this, "max_PercentAdjustment_ForSell").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getmax_InOutSum() {
		return Dispatch.get(this, "max_InOutSum").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getsupport_RS232() {
		return Dispatch.get(this, "support_RS232").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getsupport_TCPIP() {
		return Dispatch.get(this, "support_TCPIP").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getrs232_ComPort() {
		return Dispatch.get(this, "rs232_ComPort").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getrs232_BaudRate() {
		return Dispatch.get(this, "rs232_BaudRate").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getlogValue_Path() {
		return Dispatch.get(this, "logValue_Path").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getlogValue_FileName() {
		return Dispatch.get(this, "logValue_FileName").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getREPEAT_COUNT() {
		return Dispatch.get(this, "REPEAT_COUNT").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getlast_ErrorCode() {
		return Dispatch.get(this, "last_ErrorCode").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getlast_ErrorMessage() {
		return Dispatch.get(this, "last_ErrorMessage").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int oPEN_CONNECTION() {
		return Dispatch.call(this, "OPEN_CONNECTION").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cLOSE_CONNECTION() {
		return Dispatch.call(this, "CLOSE_CONNECTION").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param active an input-parameter of type boolean
	 * @return the result is of type int
	 */
	public int set_DebugMode(boolean active) {
		return Dispatch.call(this, "set_DebugMode", new Variant(active)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param command an input-parameter of type int
	 * @param inputText an input-parameter of type String
	 * @param outputText an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_Variant1(int command, String inputText, String outputText) {
		return Dispatch.call(this, "command_Variant1", new Variant(command), inputText, outputText).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param command an input-parameter of type int
	 * @param inputText an input-parameter of type String
	 * @param outputText is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_Variant1(int command, String inputText, String[] outputText) {
		Variant vnt_outputText = new Variant();
		if( outputText == null || outputText.length == 0 )
			vnt_outputText.putNoParam();
		else
			vnt_outputText.putStringRef(outputText[0]);

		int result_of_command_Variant1 = Dispatch.call(this, "command_Variant1", new Variant(command), inputText, vnt_outputText).changeType(Variant.VariantInt).getInt();

		if( outputText != null && outputText.length > 0 )
			outputText[0] = vnt_outputText.toString();

		return result_of_command_Variant1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param command an input-parameter of type int
	 * @param outputText an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_Variant2(int command, String outputText) {
		return Dispatch.call(this, "command_Variant2", new Variant(command), outputText).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param command an input-parameter of type int
	 * @param outputText is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_Variant2(int command, String[] outputText) {
		Variant vnt_outputText = new Variant();
		if( outputText == null || outputText.length == 0 )
			vnt_outputText.putNoParam();
		else
			vnt_outputText.putStringRef(outputText[0]);

		int result_of_command_Variant2 = Dispatch.call(this, "command_Variant2", new Variant(command), vnt_outputText).changeType(Variant.VariantInt).getInt();

		if( outputText != null && outputText.length > 0 )
			outputText[0] = vnt_outputText.toString();

		return result_of_command_Variant2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param command an input-parameter of type int
	 * @param inputText an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_Variant3(int command, String inputText) {
		return Dispatch.call(this, "command_Variant3", new Variant(command), inputText).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param command an input-parameter of type short
	 * @return the result is of type int
	 */
	public int command_Variant4(short command) {
		return Dispatch.call(this, "command_Variant4", command).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param targetCode an input-parameter of type int
	 * @return the result is of type String
	 */
	public String get_ErrorMessageByCode(int targetCode) {
		return Dispatch.call(this, "get_ErrorMessageByCode", new Variant(targetCode)).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param byteIndex an input-parameter of type int
	 * @param bitIndex an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean get_StatusBitState(int byteIndex, int bitIndex) {
		return Dispatch.call(this, "get_StatusBitState", new Variant(byteIndex), new Variant(bitIndex)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param byteIndex an input-parameter of type int
	 * @param bitIndex an input-parameter of type int
	 * @return the result is of type String
	 */
	public String get_StatusBitDescription(int byteIndex, int bitIndex) {
		return Dispatch.call(this, "get_StatusBitDescription", new Variant(byteIndex), new Variant(bitIndex)).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param byteIndex an input-parameter of type int
	 * @param bitIndex an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean get_StatusBitErrorChecking(int byteIndex, int bitIndex) {
		return Dispatch.call(this, "get_StatusBitErrorChecking", new Variant(byteIndex), new Variant(bitIndex)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param byteIndex an input-parameter of type int
	 * @param bitIndex an input-parameter of type int
	 * @param toValue an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean set_StatusBitErrorChecking(int byteIndex, int bitIndex, boolean toValue) {
		return Dispatch.call(this, "set_StatusBitErrorChecking", new Variant(byteIndex), new Variant(bitIndex), new Variant(toValue)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getcommunicationType() {
		return Dispatch.get(this, "communicationType").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param value an input-parameter of type int
	 * @return the result is of type int
	 */
	public int set_CommunicationType(int value) {
		return Dispatch.call(this, "set_CommunicationType", new Variant(value)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param comPort an input-parameter of type int
	 * @param baudRate an input-parameter of type int
	 * @param repeatCount an input-parameter of type int
	 * @return the result is of type int
	 */
	public int set_CommunicationRS232Values(int comPort, int baudRate, int repeatCount) {
		return Dispatch.call(this, "set_CommunicationRS232Values", new Variant(comPort), new Variant(baudRate), new Variant(repeatCount)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type short
	 */
	public short getread_TimeOut() {
		return Dispatch.get(this, "read_TimeOut").getShort();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getdealer_Code() {
		return Dispatch.get(this, "dealer_Code").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getdevice_Type() {
		return Dispatch.get(this, "device_Type").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_038(int errorCode, int slipNumber) {
		return Dispatch.call(this, "command_038", new Variant(errorCode), new Variant(slipNumber)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_038(int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_038 = Dispatch.call(this, "command_038", vnt_errorCode, vnt_slipNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_038;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_039(int errorCode, int slipNumber) {
		return Dispatch.call(this, "command_039", new Variant(errorCode), new Variant(slipNumber)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_039(int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_039 = Dispatch.call(this, "command_039", vnt_errorCode, vnt_slipNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_039;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Text an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_042(String in_Text, int errorCode) {
		return Dispatch.call(this, "command_042", in_Text, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Text an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_042(String in_Text, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_042 = Dispatch.call(this, "command_042", in_Text, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_042;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lines an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_044(int lines, int errorCode) {
		return Dispatch.call(this, "command_044", new Variant(lines), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param lines an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_044(int lines, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_044 = Dispatch.call(this, "command_044", new Variant(lines), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_044;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_045(int errorCode) {
		return Dispatch.call(this, "command_045", new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_045(int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_045 = Dispatch.call(this, "command_045", vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_045;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type int
	 * @param invoice an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_048(int opCode, String opPwd, int tillNmb, String invoice, int errorCode, int slipNumber) {
		return Dispatch.call(this, "command_048", new Variant(opCode), opPwd, new Variant(tillNmb), invoice, new Variant(errorCode), new Variant(slipNumber)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type int
	 * @param invoice an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_048(int opCode, String opPwd, int tillNmb, String invoice, int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_048 = Dispatch.call(this, "command_048", new Variant(opCode), opPwd, new Variant(tillNmb), invoice, vnt_errorCode, vnt_slipNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_048;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pluName an input-parameter of type String
	 * @param taxCd an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_049(String pluName, int taxCd, String price, String quantity, int discountType, String discountValue, int department, int errorCode, int slipNumber) {
		return Dispatch.callN(this, "command_049", new Object[] { pluName, new Variant(taxCd), price, quantity, new Variant(discountType), discountValue, new Variant(department), new Variant(errorCode), new Variant(slipNumber)}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pluName an input-parameter of type String
	 * @param taxCd an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_049(String pluName, int taxCd, String price, String quantity, int discountType, String discountValue, int department, int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_049 = Dispatch.callN(this, "command_049", new Object[] { pluName, new Variant(taxCd), price, quantity, new Variant(discountType), discountValue, new Variant(department), vnt_errorCode, vnt_slipNumber}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_049;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param nZreport an input-parameter of type int
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @param taxE an input-parameter of type String
	 * @param taxF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @param entDate an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_050(int errorCode, int nZreport, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH, String entDate) {
		return Dispatch.callN(this, "command_050", new Object[] { new Variant(errorCode), new Variant(nZreport), taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH, entDate}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param nZreport is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param entDate is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_050(int[] errorCode, int[] nZreport, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH, String[] entDate) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_nZreport = new Variant();
		if( nZreport == null || nZreport.length == 0 )
			vnt_nZreport.putNoParam();
		else
			vnt_nZreport.putIntRef(nZreport[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putStringRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putStringRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putStringRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putStringRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putStringRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putStringRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putStringRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putStringRef(taxH[0]);

		Variant vnt_entDate = new Variant();
		if( entDate == null || entDate.length == 0 )
			vnt_entDate.putNoParam();
		else
			vnt_entDate.putStringRef(entDate[0]);

		int result_of_command_050 = Dispatch.callN(this, "command_050", new Object[] { vnt_errorCode, vnt_nZreport, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_entDate}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( nZreport != null && nZreport.length > 0 )
			nZreport[0] = vnt_nZreport.toInt();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toString();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toString();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toString();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toString();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toString();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toString();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toString();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toString();
		if( entDate != null && entDate.length > 0 )
			entDate[0] = vnt_entDate.toString();

		return result_of_command_050;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param print an input-parameter of type int
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @param subTotal an input-parameter of type String
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @param taxE an input-parameter of type String
	 * @param taxF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_051(int print, int discountType, String discountValue, int errorCode, int slipNumber, String subTotal, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "command_051", new Object[] { new Variant(print), new Variant(discountType), discountValue, new Variant(errorCode), new Variant(slipNumber), subTotal, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param print an input-parameter of type int
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param subTotal is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_051(int print, int discountType, String discountValue, int[] errorCode, int[] slipNumber, String[] subTotal, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		Variant vnt_subTotal = new Variant();
		if( subTotal == null || subTotal.length == 0 )
			vnt_subTotal.putNoParam();
		else
			vnt_subTotal.putStringRef(subTotal[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putStringRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putStringRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putStringRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putStringRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putStringRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putStringRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putStringRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putStringRef(taxH[0]);

		int result_of_command_051 = Dispatch.callN(this, "command_051", new Object[] { new Variant(print), new Variant(discountType), discountValue, vnt_errorCode, vnt_slipNumber, vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();
		if( subTotal != null && subTotal.length > 0 )
			subTotal[0] = vnt_subTotal.toString();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toString();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toString();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toString();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toString();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toString();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toString();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toString();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toString();

		return result_of_command_051;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param paidMode an input-parameter of type int
	 * @param in_Amount an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param status an input-parameter of type String
	 * @param out_Amount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_053_v001(int paidMode, String in_Amount, int errorCode, String status, String out_Amount) {
		return Dispatch.call(this, "command_053_v001", new Variant(paidMode), in_Amount, new Variant(errorCode), status, out_Amount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param paidMode an input-parameter of type int
	 * @param in_Amount an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param status is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param out_Amount is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_053_v001(int paidMode, String in_Amount, int[] errorCode, String[] status, String[] out_Amount) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_status = new Variant();
		if( status == null || status.length == 0 )
			vnt_status.putNoParam();
		else
			vnt_status.putStringRef(status[0]);

		Variant vnt_out_Amount = new Variant();
		if( out_Amount == null || out_Amount.length == 0 )
			vnt_out_Amount.putNoParam();
		else
			vnt_out_Amount.putStringRef(out_Amount[0]);

		int result_of_command_053_v001 = Dispatch.call(this, "command_053_v001", new Variant(paidMode), in_Amount, vnt_errorCode, vnt_status, vnt_out_Amount).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( status != null && status.length > 0 )
			status[0] = vnt_status.toString();
		if( out_Amount != null && out_Amount.length > 0 )
			out_Amount[0] = vnt_out_Amount.toString();

		return result_of_command_053_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param paidMode an input-parameter of type int
	 * @param in_Amount an input-parameter of type String
	 * @param change an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param status an input-parameter of type String
	 * @param out_Amount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_053_v002(int paidMode, String in_Amount, int change, int errorCode, String status, String out_Amount) {
		return Dispatch.call(this, "command_053_v002", new Variant(paidMode), in_Amount, new Variant(change), new Variant(errorCode), status, out_Amount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param paidMode an input-parameter of type int
	 * @param in_Amount an input-parameter of type String
	 * @param change an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param status is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param out_Amount is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_053_v002(int paidMode, String in_Amount, int change, int[] errorCode, String[] status, String[] out_Amount) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_status = new Variant();
		if( status == null || status.length == 0 )
			vnt_status.putNoParam();
		else
			vnt_status.putStringRef(status[0]);

		Variant vnt_out_Amount = new Variant();
		if( out_Amount == null || out_Amount.length == 0 )
			vnt_out_Amount.putNoParam();
		else
			vnt_out_Amount.putStringRef(out_Amount[0]);

		int result_of_command_053_v002 = Dispatch.call(this, "command_053_v002", new Variant(paidMode), in_Amount, new Variant(change), vnt_errorCode, vnt_status, vnt_out_Amount).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( status != null && status.length > 0 )
			status[0] = vnt_status.toString();
		if( out_Amount != null && out_Amount.length > 0 )
			out_Amount[0] = vnt_out_Amount.toString();

		return result_of_command_053_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Text an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_054(String in_Text, int errorCode) {
		return Dispatch.call(this, "command_054", in_Text, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Text an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_054(String in_Text, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_054 = Dispatch.call(this, "command_054", in_Text, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_054;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_056(int errorCode, int slipNumber) {
		return Dispatch.call(this, "command_056", new Variant(errorCode), new Variant(slipNumber)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_056(int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_056 = Dispatch.call(this, "command_056", vnt_errorCode, vnt_slipNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_056;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param seller an input-parameter of type String
	 * @param receiver an input-parameter of type String
	 * @param buyer an input-parameter of type String
	 * @param address1 an input-parameter of type String
	 * @param address2 an input-parameter of type String
	 * @param typeTAXN an input-parameter of type int
	 * @param tAXN an input-parameter of type String
	 * @param vATN an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_057(String seller, String receiver, String buyer, String address1, String address2, int typeTAXN, String tAXN, String vATN, int errorCode) {
		return Dispatch.callN(this, "command_057", new Object[] { seller, receiver, buyer, address1, address2, new Variant(typeTAXN), tAXN, vATN, new Variant(errorCode)}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param seller an input-parameter of type String
	 * @param receiver an input-parameter of type String
	 * @param buyer an input-parameter of type String
	 * @param address1 an input-parameter of type String
	 * @param address2 an input-parameter of type String
	 * @param typeTAXN an input-parameter of type int
	 * @param tAXN an input-parameter of type String
	 * @param vATN an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_057(String seller, String receiver, String buyer, String address1, String address2, int typeTAXN, String tAXN, String vATN, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_057 = Dispatch.callN(this, "command_057", new Object[] { seller, receiver, buyer, address1, address2, new Variant(typeTAXN), tAXN, vATN, vnt_errorCode}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_057;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pluCode an input-parameter of type int
	 * @param quanity an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param slipNumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_058(int pluCode, String quanity, String price, int discountType, String discountValue, int errorCode, int slipNumber) {
		return Dispatch.call(this, "command_058", new Variant(pluCode), quanity, price, new Variant(discountType), discountValue, new Variant(errorCode), new Variant(slipNumber)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pluCode an input-parameter of type int
	 * @param quanity an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param discountType an input-parameter of type int
	 * @param discountValue an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param slipNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_058(int pluCode, String quanity, String price, int discountType, String discountValue, int[] errorCode, int[] slipNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_slipNumber = new Variant();
		if( slipNumber == null || slipNumber.length == 0 )
			vnt_slipNumber.putNoParam();
		else
			vnt_slipNumber.putIntRef(slipNumber[0]);

		int result_of_command_058 = Dispatch.call(this, "command_058", new Variant(pluCode), quanity, price, new Variant(discountType), discountValue, vnt_errorCode, vnt_slipNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( slipNumber != null && slipNumber.length > 0 )
			slipNumber[0] = vnt_slipNumber.toInt();

		return result_of_command_058;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_060(int errorCode) {
		return Dispatch.call(this, "command_060", new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_060(int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_060 = Dispatch.call(this, "command_060", vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_060;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dateTime an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_061(String dateTime, int errorCode) {
		return Dispatch.call(this, "command_061", dateTime, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dateTime an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_061(String dateTime, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_061 = Dispatch.call(this, "command_061", dateTime, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_061;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param dateTime an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_062(int errorCode, String dateTime) {
		return Dispatch.call(this, "command_062", new Variant(errorCode), dateTime).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_062(int[] errorCode, String[] dateTime) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putStringRef(dateTime[0]);

		int result_of_command_062 = Dispatch.call(this, "command_062", vnt_errorCode, vnt_dateTime).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toString();

		return result_of_command_062;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param nRep an input-parameter of type int
	 * @param sumA an input-parameter of type String
	 * @param sumB an input-parameter of type String
	 * @param sumC an input-parameter of type String
	 * @param sumD an input-parameter of type String
	 * @param sumE an input-parameter of type String
	 * @param sumF an input-parameter of type String
	 * @param sumG an input-parameter of type String
	 * @param sumH an input-parameter of type String
	 * @param date an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_064(int in_Type, int errorCode, int nRep, String sumA, String sumB, String sumC, String sumD, String sumE, String sumF, String sumG, String sumH, String date) {
		return Dispatch.callN(this, "command_064", new Object[] { new Variant(in_Type), new Variant(errorCode), new Variant(nRep), sumA, sumB, sumC, sumD, sumE, sumF, sumG, sumH, date}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param nRep is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param date is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_064(int in_Type, int[] errorCode, int[] nRep, String[] sumA, String[] sumB, String[] sumC, String[] sumD, String[] sumE, String[] sumF, String[] sumG, String[] sumH, String[] date) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_nRep = new Variant();
		if( nRep == null || nRep.length == 0 )
			vnt_nRep.putNoParam();
		else
			vnt_nRep.putIntRef(nRep[0]);

		Variant vnt_sumA = new Variant();
		if( sumA == null || sumA.length == 0 )
			vnt_sumA.putNoParam();
		else
			vnt_sumA.putStringRef(sumA[0]);

		Variant vnt_sumB = new Variant();
		if( sumB == null || sumB.length == 0 )
			vnt_sumB.putNoParam();
		else
			vnt_sumB.putStringRef(sumB[0]);

		Variant vnt_sumC = new Variant();
		if( sumC == null || sumC.length == 0 )
			vnt_sumC.putNoParam();
		else
			vnt_sumC.putStringRef(sumC[0]);

		Variant vnt_sumD = new Variant();
		if( sumD == null || sumD.length == 0 )
			vnt_sumD.putNoParam();
		else
			vnt_sumD.putStringRef(sumD[0]);

		Variant vnt_sumE = new Variant();
		if( sumE == null || sumE.length == 0 )
			vnt_sumE.putNoParam();
		else
			vnt_sumE.putStringRef(sumE[0]);

		Variant vnt_sumF = new Variant();
		if( sumF == null || sumF.length == 0 )
			vnt_sumF.putNoParam();
		else
			vnt_sumF.putStringRef(sumF[0]);

		Variant vnt_sumG = new Variant();
		if( sumG == null || sumG.length == 0 )
			vnt_sumG.putNoParam();
		else
			vnt_sumG.putStringRef(sumG[0]);

		Variant vnt_sumH = new Variant();
		if( sumH == null || sumH.length == 0 )
			vnt_sumH.putNoParam();
		else
			vnt_sumH.putStringRef(sumH[0]);

		Variant vnt_date = new Variant();
		if( date == null || date.length == 0 )
			vnt_date.putNoParam();
		else
			vnt_date.putStringRef(date[0]);

		int result_of_command_064 = Dispatch.callN(this, "command_064", new Object[] { new Variant(in_Type), vnt_errorCode, vnt_nRep, vnt_sumA, vnt_sumB, vnt_sumC, vnt_sumD, vnt_sumE, vnt_sumF, vnt_sumG, vnt_sumH, vnt_date}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( nRep != null && nRep.length > 0 )
			nRep[0] = vnt_nRep.toInt();
		if( sumA != null && sumA.length > 0 )
			sumA[0] = vnt_sumA.toString();
		if( sumB != null && sumB.length > 0 )
			sumB[0] = vnt_sumB.toString();
		if( sumC != null && sumC.length > 0 )
			sumC[0] = vnt_sumC.toString();
		if( sumD != null && sumD.length > 0 )
			sumD[0] = vnt_sumD.toString();
		if( sumE != null && sumE.length > 0 )
			sumE[0] = vnt_sumE.toString();
		if( sumF != null && sumF.length > 0 )
			sumF[0] = vnt_sumF.toString();
		if( sumG != null && sumG.length > 0 )
			sumG[0] = vnt_sumG.toString();
		if( sumH != null && sumH.length > 0 )
			sumH[0] = vnt_sumH.toString();
		if( date != null && date.length > 0 )
			date[0] = vnt_date.toString();

		return result_of_command_064;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param nRep an input-parameter of type int
	 * @param sumA an input-parameter of type String
	 * @param sumB an input-parameter of type String
	 * @param sumC an input-parameter of type String
	 * @param sumD an input-parameter of type String
	 * @param sumE an input-parameter of type String
	 * @param sumF an input-parameter of type String
	 * @param sumG an input-parameter of type String
	 * @param sumH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_065(int in_Type, int errorCode, int nRep, String sumA, String sumB, String sumC, String sumD, String sumE, String sumF, String sumG, String sumH) {
		return Dispatch.callN(this, "command_065", new Object[] { new Variant(in_Type), new Variant(errorCode), new Variant(nRep), sumA, sumB, sumC, sumD, sumE, sumF, sumG, sumH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param nRep is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sumH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_065(int in_Type, int[] errorCode, int[] nRep, String[] sumA, String[] sumB, String[] sumC, String[] sumD, String[] sumE, String[] sumF, String[] sumG, String[] sumH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_nRep = new Variant();
		if( nRep == null || nRep.length == 0 )
			vnt_nRep.putNoParam();
		else
			vnt_nRep.putIntRef(nRep[0]);

		Variant vnt_sumA = new Variant();
		if( sumA == null || sumA.length == 0 )
			vnt_sumA.putNoParam();
		else
			vnt_sumA.putStringRef(sumA[0]);

		Variant vnt_sumB = new Variant();
		if( sumB == null || sumB.length == 0 )
			vnt_sumB.putNoParam();
		else
			vnt_sumB.putStringRef(sumB[0]);

		Variant vnt_sumC = new Variant();
		if( sumC == null || sumC.length == 0 )
			vnt_sumC.putNoParam();
		else
			vnt_sumC.putStringRef(sumC[0]);

		Variant vnt_sumD = new Variant();
		if( sumD == null || sumD.length == 0 )
			vnt_sumD.putNoParam();
		else
			vnt_sumD.putStringRef(sumD[0]);

		Variant vnt_sumE = new Variant();
		if( sumE == null || sumE.length == 0 )
			vnt_sumE.putNoParam();
		else
			vnt_sumE.putStringRef(sumE[0]);

		Variant vnt_sumF = new Variant();
		if( sumF == null || sumF.length == 0 )
			vnt_sumF.putNoParam();
		else
			vnt_sumF.putStringRef(sumF[0]);

		Variant vnt_sumG = new Variant();
		if( sumG == null || sumG.length == 0 )
			vnt_sumG.putNoParam();
		else
			vnt_sumG.putStringRef(sumG[0]);

		Variant vnt_sumH = new Variant();
		if( sumH == null || sumH.length == 0 )
			vnt_sumH.putNoParam();
		else
			vnt_sumH.putStringRef(sumH[0]);

		int result_of_command_065 = Dispatch.callN(this, "command_065", new Object[] { new Variant(in_Type), vnt_errorCode, vnt_nRep, vnt_sumA, vnt_sumB, vnt_sumC, vnt_sumD, vnt_sumE, vnt_sumF, vnt_sumG, vnt_sumH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( nRep != null && nRep.length > 0 )
			nRep[0] = vnt_nRep.toInt();
		if( sumA != null && sumA.length > 0 )
			sumA[0] = vnt_sumA.toString();
		if( sumB != null && sumB.length > 0 )
			sumB[0] = vnt_sumB.toString();
		if( sumC != null && sumC.length > 0 )
			sumC[0] = vnt_sumC.toString();
		if( sumD != null && sumD.length > 0 )
			sumD[0] = vnt_sumD.toString();
		if( sumE != null && sumE.length > 0 )
			sumE[0] = vnt_sumE.toString();
		if( sumF != null && sumF.length > 0 )
			sumF[0] = vnt_sumF.toString();
		if( sumG != null && sumG.length > 0 )
			sumG[0] = vnt_sumG.toString();
		if( sumH != null && sumH.length > 0 )
			sumH[0] = vnt_sumH.toString();

		return result_of_command_065;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Start an input-parameter of type int
	 * @param in_End an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_Start an input-parameter of type int
	 * @param out_End an input-parameter of type int
	 * @param cURRENT an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_066_v001(int in_Start, int in_End, int errorCode, int out_Start, int out_End, int cURRENT) {
		return Dispatch.call(this, "command_066_v001", new Variant(in_Start), new Variant(in_End), new Variant(errorCode), new Variant(out_Start), new Variant(out_End), new Variant(cURRENT)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Start an input-parameter of type int
	 * @param in_End an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_Start is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_End is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param cURRENT is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_066_v001(int in_Start, int in_End, int[] errorCode, int[] out_Start, int[] out_End, int[] cURRENT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_Start = new Variant();
		if( out_Start == null || out_Start.length == 0 )
			vnt_out_Start.putNoParam();
		else
			vnt_out_Start.putIntRef(out_Start[0]);

		Variant vnt_out_End = new Variant();
		if( out_End == null || out_End.length == 0 )
			vnt_out_End.putNoParam();
		else
			vnt_out_End.putIntRef(out_End[0]);

		Variant vnt_cURRENT = new Variant();
		if( cURRENT == null || cURRENT.length == 0 )
			vnt_cURRENT.putNoParam();
		else
			vnt_cURRENT.putIntRef(cURRENT[0]);

		int result_of_command_066_v001 = Dispatch.call(this, "command_066_v001", new Variant(in_Start), new Variant(in_End), vnt_errorCode, vnt_out_Start, vnt_out_End, vnt_cURRENT).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_Start != null && out_Start.length > 0 )
			out_Start[0] = vnt_out_Start.toInt();
		if( out_End != null && out_End.length > 0 )
			out_End[0] = vnt_out_End.toInt();
		if( cURRENT != null && cURRENT.length > 0 )
			cURRENT[0] = vnt_cURRENT.toInt();

		return result_of_command_066_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_End an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param start an input-parameter of type int
	 * @param out_End an input-parameter of type int
	 * @param cURRENT an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_066_v002(int in_End, int errorCode, int start, int out_End, int cURRENT) {
		return Dispatch.call(this, "command_066_v002", new Variant(in_End), new Variant(errorCode), new Variant(start), new Variant(out_End), new Variant(cURRENT)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_End an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param start is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param out_End is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param cURRENT is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_066_v002(int in_End, int[] errorCode, int[] start, int[] out_End, int[] cURRENT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_start = new Variant();
		if( start == null || start.length == 0 )
			vnt_start.putNoParam();
		else
			vnt_start.putIntRef(start[0]);

		Variant vnt_out_End = new Variant();
		if( out_End == null || out_End.length == 0 )
			vnt_out_End.putNoParam();
		else
			vnt_out_End.putIntRef(out_End[0]);

		Variant vnt_cURRENT = new Variant();
		if( cURRENT == null || cURRENT.length == 0 )
			vnt_cURRENT.putNoParam();
		else
			vnt_cURRENT.putIntRef(cURRENT[0]);

		int result_of_command_066_v002 = Dispatch.call(this, "command_066_v002", new Variant(in_End), vnt_errorCode, vnt_start, vnt_out_End, vnt_cURRENT).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( start != null && start.length > 0 )
			start[0] = vnt_start.toInt();
		if( out_End != null && out_End.length > 0 )
			out_End[0] = vnt_out_End.toInt();
		if( cURRENT != null && cURRENT.length > 0 )
			cURRENT[0] = vnt_cURRENT.toInt();

		return result_of_command_066_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param reportsLeft an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_068(int errorCode, int reportsLeft) {
		return Dispatch.call(this, "command_068", new Variant(errorCode), new Variant(reportsLeft)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param reportsLeft is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_068(int[] errorCode, int[] reportsLeft) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_reportsLeft = new Variant();
		if( reportsLeft == null || reportsLeft.length == 0 )
			vnt_reportsLeft.putNoParam();
		else
			vnt_reportsLeft.putIntRef(reportsLeft[0]);

		int result_of_command_068 = Dispatch.call(this, "command_068", vnt_errorCode, vnt_reportsLeft).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( reportsLeft != null && reportsLeft.length > 0 )
			reportsLeft[0] = vnt_reportsLeft.toInt();

		return result_of_command_068;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param nRep an input-parameter of type int
	 * @param totA an input-parameter of type String
	 * @param totB an input-parameter of type String
	 * @param totC an input-parameter of type String
	 * @param totD an input-parameter of type String
	 * @param totE an input-parameter of type String
	 * @param totF an input-parameter of type String
	 * @param totG an input-parameter of type String
	 * @param totH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_069(String reportType, int errorCode, int nRep, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "command_069", new Object[] { reportType, new Variant(errorCode), new Variant(nRep), totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param nRep is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_069(String reportType, int[] errorCode, int[] nRep, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_nRep = new Variant();
		if( nRep == null || nRep.length == 0 )
			vnt_nRep.putNoParam();
		else
			vnt_nRep.putIntRef(nRep[0]);

		Variant vnt_totA = new Variant();
		if( totA == null || totA.length == 0 )
			vnt_totA.putNoParam();
		else
			vnt_totA.putStringRef(totA[0]);

		Variant vnt_totB = new Variant();
		if( totB == null || totB.length == 0 )
			vnt_totB.putNoParam();
		else
			vnt_totB.putStringRef(totB[0]);

		Variant vnt_totC = new Variant();
		if( totC == null || totC.length == 0 )
			vnt_totC.putNoParam();
		else
			vnt_totC.putStringRef(totC[0]);

		Variant vnt_totD = new Variant();
		if( totD == null || totD.length == 0 )
			vnt_totD.putNoParam();
		else
			vnt_totD.putStringRef(totD[0]);

		Variant vnt_totE = new Variant();
		if( totE == null || totE.length == 0 )
			vnt_totE.putNoParam();
		else
			vnt_totE.putStringRef(totE[0]);

		Variant vnt_totF = new Variant();
		if( totF == null || totF.length == 0 )
			vnt_totF.putNoParam();
		else
			vnt_totF.putStringRef(totF[0]);

		Variant vnt_totG = new Variant();
		if( totG == null || totG.length == 0 )
			vnt_totG.putNoParam();
		else
			vnt_totG.putStringRef(totG[0]);

		Variant vnt_totH = new Variant();
		if( totH == null || totH.length == 0 )
			vnt_totH.putNoParam();
		else
			vnt_totH.putStringRef(totH[0]);

		int result_of_command_069 = Dispatch.callN(this, "command_069", new Object[] { reportType, vnt_errorCode, vnt_nRep, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( nRep != null && nRep.length > 0 )
			nRep[0] = vnt_nRep.toInt();
		if( totA != null && totA.length > 0 )
			totA[0] = vnt_totA.toString();
		if( totB != null && totB.length > 0 )
			totB[0] = vnt_totB.toString();
		if( totC != null && totC.length > 0 )
			totC[0] = vnt_totC.toString();
		if( totD != null && totD.length > 0 )
			totD[0] = vnt_totD.toString();
		if( totE != null && totE.length > 0 )
			totE[0] = vnt_totE.toString();
		if( totF != null && totF.length > 0 )
			totF[0] = vnt_totF.toString();
		if( totG != null && totG.length > 0 )
			totG[0] = vnt_totG.toString();
		if( totH != null && totH.length > 0 )
			totH[0] = vnt_totH.toString();

		return result_of_command_069;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param amount an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param cashSum an input-parameter of type String
	 * @param cashIn an input-parameter of type String
	 * @param cashOut an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_070(int in_Type, String amount, int errorCode, String cashSum, String cashIn, String cashOut) {
		return Dispatch.call(this, "command_070", new Variant(in_Type), amount, new Variant(errorCode), cashSum, cashIn, cashOut).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param amount an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param cashSum is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param cashIn is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param cashOut is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_070(int in_Type, String amount, int[] errorCode, String[] cashSum, String[] cashIn, String[] cashOut) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_cashSum = new Variant();
		if( cashSum == null || cashSum.length == 0 )
			vnt_cashSum.putNoParam();
		else
			vnt_cashSum.putStringRef(cashSum[0]);

		Variant vnt_cashIn = new Variant();
		if( cashIn == null || cashIn.length == 0 )
			vnt_cashIn.putNoParam();
		else
			vnt_cashIn.putStringRef(cashIn[0]);

		Variant vnt_cashOut = new Variant();
		if( cashOut == null || cashOut.length == 0 )
			vnt_cashOut.putNoParam();
		else
			vnt_cashOut.putStringRef(cashOut[0]);

		int result_of_command_070 = Dispatch.call(this, "command_070", new Variant(in_Type), amount, vnt_errorCode, vnt_cashSum, vnt_cashIn, vnt_cashOut).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( cashSum != null && cashSum.length > 0 )
			cashSum[0] = vnt_cashSum.toString();
		if( cashIn != null && cashIn.length > 0 )
			cashIn[0] = vnt_cashIn.toString();
		if( cashOut != null && cashOut.length > 0 )
			cashOut[0] = vnt_cashOut.toString();

		return result_of_command_070;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param infoType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_071(int infoType, int errorCode) {
		return Dispatch.call(this, "command_071", new Variant(infoType), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param infoType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_071(int infoType, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_071 = Dispatch.call(this, "command_071", new Variant(infoType), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_071;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param serialNumber an input-parameter of type String
	 * @param taxNumber an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_072(String serialNumber, String taxNumber, int errorCode) {
		return Dispatch.call(this, "command_072", serialNumber, taxNumber, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param serialNumber an input-parameter of type String
	 * @param taxNumber an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_072(String serialNumber, String taxNumber, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_072 = Dispatch.call(this, "command_072", serialNumber, taxNumber, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_072;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param statusBytes an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_074(int errorCode, String statusBytes) {
		return Dispatch.call(this, "command_074", new Variant(errorCode), statusBytes).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param statusBytes is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_074(int[] errorCode, String[] statusBytes) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_statusBytes = new Variant();
		if( statusBytes == null || statusBytes.length == 0 )
			vnt_statusBytes.putNoParam();
		else
			vnt_statusBytes.putStringRef(statusBytes[0]);

		int result_of_command_074 = Dispatch.call(this, "command_074", vnt_errorCode, vnt_statusBytes).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( statusBytes != null && statusBytes.length > 0 )
			statusBytes[0] = vnt_statusBytes.toString();

		return result_of_command_074;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param isOpen an input-parameter of type int
	 * @param number an input-parameter of type int
	 * @param items an input-parameter of type int
	 * @param amount an input-parameter of type String
	 * @param payed an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_076(int errorCode, int isOpen, int number, int items, String amount, String payed) {
		return Dispatch.call(this, "command_076", new Variant(errorCode), new Variant(isOpen), new Variant(number), new Variant(items), amount, payed).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param isOpen is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param number is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param items is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param payed is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_076(int[] errorCode, int[] isOpen, int[] number, int[] items, String[] amount, String[] payed) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_isOpen = new Variant();
		if( isOpen == null || isOpen.length == 0 )
			vnt_isOpen.putNoParam();
		else
			vnt_isOpen.putIntRef(isOpen[0]);

		Variant vnt_number = new Variant();
		if( number == null || number.length == 0 )
			vnt_number.putNoParam();
		else
			vnt_number.putIntRef(number[0]);

		Variant vnt_items = new Variant();
		if( items == null || items.length == 0 )
			vnt_items.putNoParam();
		else
			vnt_items.putIntRef(items[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putStringRef(amount[0]);

		Variant vnt_payed = new Variant();
		if( payed == null || payed.length == 0 )
			vnt_payed.putNoParam();
		else
			vnt_payed.putStringRef(payed[0]);

		int result_of_command_076 = Dispatch.call(this, "command_076", vnt_errorCode, vnt_isOpen, vnt_number, vnt_items, vnt_amount, vnt_payed).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( isOpen != null && isOpen.length > 0 )
			isOpen[0] = vnt_isOpen.toInt();
		if( number != null && number.length > 0 )
			number[0] = vnt_number.toInt();
		if( items != null && items.length > 0 )
			items[0] = vnt_items.toInt();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toString();
		if( payed != null && payed.length > 0 )
			payed[0] = vnt_payed.toString();

		return result_of_command_076;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param hz an input-parameter of type int
	 * @param mSec an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_080(int hz, int mSec, int errorCode) {
		return Dispatch.call(this, "command_080", new Variant(hz), new Variant(mSec), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param hz an input-parameter of type int
	 * @param mSec an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_080(int hz, int mSec, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_080 = Dispatch.call(this, "command_080", new Variant(hz), new Variant(mSec), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_080;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @param taxE an input-parameter of type String
	 * @param taxF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @param decimal_point an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param remainingChanges an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_083(String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH, int decimal_point, int errorCode, int remainingChanges) {
		return Dispatch.callN(this, "command_083", new Object[] { taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH, new Variant(decimal_point), new Variant(errorCode), new Variant(remainingChanges)}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @param taxE an input-parameter of type String
	 * @param taxF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @param decimal_point an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param remainingChanges is an one-element array which sends the input-parameter
	 *                         to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_083(String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH, int decimal_point, int[] errorCode, int[] remainingChanges) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_remainingChanges = new Variant();
		if( remainingChanges == null || remainingChanges.length == 0 )
			vnt_remainingChanges.putNoParam();
		else
			vnt_remainingChanges.putIntRef(remainingChanges[0]);

		int result_of_command_083 = Dispatch.callN(this, "command_083", new Object[] { taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH, new Variant(decimal_point), vnt_errorCode, vnt_remainingChanges}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( remainingChanges != null && remainingChanges.length > 0 )
			remainingChanges[0] = vnt_remainingChanges.toInt();

		return result_of_command_083;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param dateTime an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_086(int errorCode, String dateTime) {
		return Dispatch.call(this, "command_086", new Variant(errorCode), dateTime).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_086(int[] errorCode, String[] dateTime) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putStringRef(dateTime[0]);

		int result_of_command_086 = Dispatch.call(this, "command_086", vnt_errorCode, vnt_dateTime).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toString();

		return result_of_command_086;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param itemGroup an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param totSales an input-parameter of type int
	 * @param totSum an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_087(String itemGroup, int errorCode, int totSales, String totSum, String name) {
		return Dispatch.call(this, "command_087", itemGroup, new Variant(errorCode), new Variant(totSales), totSum, name).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param itemGroup an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param totSales is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param totSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_087(String itemGroup, int[] errorCode, int[] totSales, String[] totSum, String[] name) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_totSales = new Variant();
		if( totSales == null || totSales.length == 0 )
			vnt_totSales.putNoParam();
		else
			vnt_totSales.putIntRef(totSales[0]);

		Variant vnt_totSum = new Variant();
		if( totSum == null || totSum.length == 0 )
			vnt_totSum.putNoParam();
		else
			vnt_totSum.putStringRef(totSum[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		int result_of_command_087 = Dispatch.call(this, "command_087", itemGroup, vnt_errorCode, vnt_totSales, vnt_totSum, vnt_name).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( totSales != null && totSales.length > 0 )
			totSales[0] = vnt_totSales.toInt();
		if( totSum != null && totSum.length > 0 )
			totSum[0] = vnt_totSum.toString();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_command_087;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param departmentNumber an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param totSales an input-parameter of type String
	 * @param totSum an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_088(int departmentNumber, int errorCode, String taxGr, String price, String totSales, String totSum, String name) {
		return Dispatch.call(this, "command_088", new Variant(departmentNumber), new Variant(errorCode), taxGr, price, totSales, totSum, name).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param departmentNumber an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param totSales is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param totSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_088(int departmentNumber, int[] errorCode, String[] taxGr, String[] price, String[] totSales, String[] totSum, String[] name) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_totSales = new Variant();
		if( totSales == null || totSales.length == 0 )
			vnt_totSales.putNoParam();
		else
			vnt_totSales.putStringRef(totSales[0]);

		Variant vnt_totSum = new Variant();
		if( totSum == null || totSum.length == 0 )
			vnt_totSum.putNoParam();
		else
			vnt_totSum.putStringRef(totSum[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		int result_of_command_088 = Dispatch.call(this, "command_088", new Variant(departmentNumber), vnt_errorCode, vnt_taxGr, vnt_price, vnt_totSales, vnt_totSum, vnt_name).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( totSales != null && totSales.length > 0 )
			totSales[0] = vnt_totSales.toString();
		if( totSum != null && totSum.length > 0 )
			totSum[0] = vnt_totSum.toString();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_command_088;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param testType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param records an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_089(int testType, int errorCode, int records) {
		return Dispatch.call(this, "command_089", new Variant(testType), new Variant(errorCode), new Variant(records)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param testType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param records is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_089(int testType, int[] errorCode, int[] records) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_records = new Variant();
		if( records == null || records.length == 0 )
			vnt_records.putNoParam();
		else
			vnt_records.putIntRef(records[0]);

		int result_of_command_089 = Dispatch.call(this, "command_089", new Variant(testType), vnt_errorCode, vnt_records).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( records != null && records.length > 0 )
			records[0] = vnt_records.toInt();

		return result_of_command_089;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param newFormat an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param devicename an input-parameter of type String
	 * @param fwRev an input-parameter of type String
	 * @param fwDate an input-parameter of type String
	 * @param fwTime an input-parameter of type String
	 * @param checksum an input-parameter of type String
	 * @param sw an input-parameter of type String
	 * @param serialNumber an input-parameter of type String
	 * @param fMNumber an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_090_v001(String newFormat, int errorCode, String devicename, String fwRev, String fwDate, String fwTime, String checksum, String sw, String serialNumber, String fMNumber) {
		return Dispatch.callN(this, "command_090_v001", new Object[] { newFormat, new Variant(errorCode), devicename, fwRev, fwDate, fwTime, checksum, sw, serialNumber, fMNumber}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param newFormat an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param devicename is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fwRev is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param fwDate is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fwTime is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param checksum is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param sw is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param serialNumber is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param fMNumber is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_090_v001(String newFormat, int[] errorCode, String[] devicename, String[] fwRev, String[] fwDate, String[] fwTime, String[] checksum, String[] sw, String[] serialNumber, String[] fMNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_devicename = new Variant();
		if( devicename == null || devicename.length == 0 )
			vnt_devicename.putNoParam();
		else
			vnt_devicename.putStringRef(devicename[0]);

		Variant vnt_fwRev = new Variant();
		if( fwRev == null || fwRev.length == 0 )
			vnt_fwRev.putNoParam();
		else
			vnt_fwRev.putStringRef(fwRev[0]);

		Variant vnt_fwDate = new Variant();
		if( fwDate == null || fwDate.length == 0 )
			vnt_fwDate.putNoParam();
		else
			vnt_fwDate.putStringRef(fwDate[0]);

		Variant vnt_fwTime = new Variant();
		if( fwTime == null || fwTime.length == 0 )
			vnt_fwTime.putNoParam();
		else
			vnt_fwTime.putStringRef(fwTime[0]);

		Variant vnt_checksum = new Variant();
		if( checksum == null || checksum.length == 0 )
			vnt_checksum.putNoParam();
		else
			vnt_checksum.putStringRef(checksum[0]);

		Variant vnt_sw = new Variant();
		if( sw == null || sw.length == 0 )
			vnt_sw.putNoParam();
		else
			vnt_sw.putStringRef(sw[0]);

		Variant vnt_serialNumber = new Variant();
		if( serialNumber == null || serialNumber.length == 0 )
			vnt_serialNumber.putNoParam();
		else
			vnt_serialNumber.putStringRef(serialNumber[0]);

		Variant vnt_fMNumber = new Variant();
		if( fMNumber == null || fMNumber.length == 0 )
			vnt_fMNumber.putNoParam();
		else
			vnt_fMNumber.putStringRef(fMNumber[0]);

		int result_of_command_090_v001 = Dispatch.callN(this, "command_090_v001", new Object[] { newFormat, vnt_errorCode, vnt_devicename, vnt_fwRev, vnt_fwDate, vnt_fwTime, vnt_checksum, vnt_sw, vnt_serialNumber, vnt_fMNumber}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( devicename != null && devicename.length > 0 )
			devicename[0] = vnt_devicename.toString();
		if( fwRev != null && fwRev.length > 0 )
			fwRev[0] = vnt_fwRev.toString();
		if( fwDate != null && fwDate.length > 0 )
			fwDate[0] = vnt_fwDate.toString();
		if( fwTime != null && fwTime.length > 0 )
			fwTime[0] = vnt_fwTime.toString();
		if( checksum != null && checksum.length > 0 )
			checksum[0] = vnt_checksum.toString();
		if( sw != null && sw.length > 0 )
			sw[0] = vnt_sw.toString();
		if( serialNumber != null && serialNumber.length > 0 )
			serialNumber[0] = vnt_serialNumber.toString();
		if( fMNumber != null && fMNumber.length > 0 )
			fMNumber[0] = vnt_fMNumber.toString();

		return result_of_command_090_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param calculate an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param devicename an input-parameter of type String
	 * @param fwRev an input-parameter of type String
	 * @param fwDate an input-parameter of type String
	 * @param fwTime an input-parameter of type String
	 * @param checksum an input-parameter of type String
	 * @param sw an input-parameter of type String
	 * @param serialNumber an input-parameter of type String
	 * @param fMNumber an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_090_v002(int calculate, int errorCode, String devicename, String fwRev, String fwDate, String fwTime, String checksum, String sw, String serialNumber, String fMNumber) {
		return Dispatch.callN(this, "command_090_v002", new Object[] { new Variant(calculate), new Variant(errorCode), devicename, fwRev, fwDate, fwTime, checksum, sw, serialNumber, fMNumber}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param calculate an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param devicename is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fwRev is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param fwDate is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fwTime is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param checksum is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param sw is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param serialNumber is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param fMNumber is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_090_v002(int calculate, int[] errorCode, String[] devicename, String[] fwRev, String[] fwDate, String[] fwTime, String[] checksum, String[] sw, String[] serialNumber, String[] fMNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_devicename = new Variant();
		if( devicename == null || devicename.length == 0 )
			vnt_devicename.putNoParam();
		else
			vnt_devicename.putStringRef(devicename[0]);

		Variant vnt_fwRev = new Variant();
		if( fwRev == null || fwRev.length == 0 )
			vnt_fwRev.putNoParam();
		else
			vnt_fwRev.putStringRef(fwRev[0]);

		Variant vnt_fwDate = new Variant();
		if( fwDate == null || fwDate.length == 0 )
			vnt_fwDate.putNoParam();
		else
			vnt_fwDate.putStringRef(fwDate[0]);

		Variant vnt_fwTime = new Variant();
		if( fwTime == null || fwTime.length == 0 )
			vnt_fwTime.putNoParam();
		else
			vnt_fwTime.putStringRef(fwTime[0]);

		Variant vnt_checksum = new Variant();
		if( checksum == null || checksum.length == 0 )
			vnt_checksum.putNoParam();
		else
			vnt_checksum.putStringRef(checksum[0]);

		Variant vnt_sw = new Variant();
		if( sw == null || sw.length == 0 )
			vnt_sw.putNoParam();
		else
			vnt_sw.putStringRef(sw[0]);

		Variant vnt_serialNumber = new Variant();
		if( serialNumber == null || serialNumber.length == 0 )
			vnt_serialNumber.putNoParam();
		else
			vnt_serialNumber.putStringRef(serialNumber[0]);

		Variant vnt_fMNumber = new Variant();
		if( fMNumber == null || fMNumber.length == 0 )
			vnt_fMNumber.putNoParam();
		else
			vnt_fMNumber.putStringRef(fMNumber[0]);

		int result_of_command_090_v002 = Dispatch.callN(this, "command_090_v002", new Object[] { new Variant(calculate), vnt_errorCode, vnt_devicename, vnt_fwRev, vnt_fwDate, vnt_fwTime, vnt_checksum, vnt_sw, vnt_serialNumber, vnt_fMNumber}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( devicename != null && devicename.length > 0 )
			devicename[0] = vnt_devicename.toString();
		if( fwRev != null && fwRev.length > 0 )
			fwRev[0] = vnt_fwRev.toString();
		if( fwDate != null && fwDate.length > 0 )
			fwDate[0] = vnt_fwDate.toString();
		if( fwTime != null && fwTime.length > 0 )
			fwTime[0] = vnt_fwTime.toString();
		if( checksum != null && checksum.length > 0 )
			checksum[0] = vnt_checksum.toString();
		if( sw != null && sw.length > 0 )
			sw[0] = vnt_sw.toString();
		if( serialNumber != null && serialNumber.length > 0 )
			serialNumber[0] = vnt_serialNumber.toString();
		if( fMNumber != null && fMNumber.length > 0 )
			fMNumber[0] = vnt_fMNumber.toString();

		return result_of_command_090_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param serialNumber an input-parameter of type String
	 * @param fMNumber an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param country an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_091(String serialNumber, String fMNumber, int errorCode, String country) {
		return Dispatch.call(this, "command_091", serialNumber, fMNumber, new Variant(errorCode), country).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param serialNumber an input-parameter of type String
	 * @param fMNumber an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param country is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_091(String serialNumber, String fMNumber, int[] errorCode, String[] country) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_country = new Variant();
		if( country == null || country.length == 0 )
			vnt_country.putNoParam();
		else
			vnt_country.putStringRef(country[0]);

		int result_of_command_091 = Dispatch.call(this, "command_091", serialNumber, fMNumber, vnt_errorCode, vnt_country).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( country != null && country.length > 0 )
			country[0] = vnt_country.toString();

		return result_of_command_091;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_092(int in_Type, int errorCode) {
		return Dispatch.call(this, "command_092", new Variant(in_Type), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_092(int in_Type, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_092 = Dispatch.call(this, "command_092", new Variant(in_Type), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_092;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_094(int in_Type, String startDate, String endDate, int errorCode) {
		return Dispatch.call(this, "command_094", new Variant(in_Type), startDate, endDate, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_094(int in_Type, String startDate, String endDate, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_094 = Dispatch.call(this, "command_094", new Variant(in_Type), startDate, endDate, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_094;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param startNumber an input-parameter of type int
	 * @param endNumber an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_095(int in_Type, int startNumber, int endNumber, int errorCode) {
		return Dispatch.call(this, "command_095", new Variant(in_Type), new Variant(startNumber), new Variant(endNumber), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Type an input-parameter of type int
	 * @param startNumber an input-parameter of type int
	 * @param endNumber an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_095(int in_Type, int startNumber, int endNumber, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_095 = Dispatch.call(this, "command_095", new Variant(in_Type), new Variant(startNumber), new Variant(endNumber), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_095;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxNumber an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_098(String taxNumber, int errorCode) {
		return Dispatch.call(this, "command_098", taxNumber, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxNumber an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_098(String taxNumber, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_098 = Dispatch.call(this, "command_098", taxNumber, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_098;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param taxNumber an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_099(int errorCode, String taxNumber) {
		return Dispatch.call(this, "command_099", new Variant(errorCode), taxNumber).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param taxNumber is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_099(int[] errorCode, String[] taxNumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_taxNumber = new Variant();
		if( taxNumber == null || taxNumber.length == 0 )
			vnt_taxNumber.putNoParam();
		else
			vnt_taxNumber.putStringRef(taxNumber[0]);

		int result_of_command_099 = Dispatch.call(this, "command_099", vnt_errorCode, vnt_taxNumber).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( taxNumber != null && taxNumber.length > 0 )
			taxNumber[0] = vnt_taxNumber.toString();

		return result_of_command_099;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Code an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_Code an input-parameter of type int
	 * @param errorMessage an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_100(int in_Code, int errorCode, int out_Code, String errorMessage) {
		return Dispatch.call(this, "command_100", new Variant(in_Code), new Variant(errorCode), new Variant(out_Code), errorMessage).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Code an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_Code is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param errorMessage is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_100(int in_Code, int[] errorCode, int[] out_Code, String[] errorMessage) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_Code = new Variant();
		if( out_Code == null || out_Code.length == 0 )
			vnt_out_Code.putNoParam();
		else
			vnt_out_Code.putIntRef(out_Code[0]);

		Variant vnt_errorMessage = new Variant();
		if( errorMessage == null || errorMessage.length == 0 )
			vnt_errorMessage.putNoParam();
		else
			vnt_errorMessage.putStringRef(errorMessage[0]);

		int result_of_command_100 = Dispatch.call(this, "command_100", new Variant(in_Code), vnt_errorCode, vnt_out_Code, vnt_errorMessage).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_Code != null && out_Code.length > 0 )
			out_Code[0] = vnt_out_Code.toInt();
		if( errorMessage != null && errorMessage.length > 0 )
			errorMessage[0] = vnt_errorMessage.toString();

		return result_of_command_100;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param oldPassword an input-parameter of type String
	 * @param newPassword an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_101(int operatorCode, String oldPassword, String newPassword, int errorCode) {
		return Dispatch.call(this, "command_101", new Variant(operatorCode), oldPassword, newPassword, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param oldPassword an input-parameter of type String
	 * @param newPassword an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_101(int operatorCode, String oldPassword, String newPassword, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_101 = Dispatch.call(this, "command_101", new Variant(operatorCode), oldPassword, newPassword, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_101;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @param sumVATA an input-parameter of type String
	 * @param sumVATB an input-parameter of type String
	 * @param sumVATC an input-parameter of type String
	 * @param sumVATD an input-parameter of type String
	 * @param sumVATE an input-parameter of type String
	 * @param sumVATF an input-parameter of type String
	 * @param sumVATG an input-parameter of type String
	 * @param sumVATH an input-parameter of type String
	 * @param inv an input-parameter of type int
	 * @param invNmb an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_103(int errorCode, String sumVATA, String sumVATB, String sumVATC, String sumVATD, String sumVATE, String sumVATF, String sumVATG, String sumVATH, int inv, String invNmb) {
		return Dispatch.callN(this, "command_103", new Object[] { new Variant(errorCode), sumVATA, sumVATB, sumVATC, sumVATD, sumVATE, sumVATF, sumVATG, sumVATH, new Variant(inv), invNmb}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param sumVATA is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATB is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATC is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATD is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATE is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATF is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATG is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param sumVATH is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param inv is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param invNmb is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_103(int[] errorCode, String[] sumVATA, String[] sumVATB, String[] sumVATC, String[] sumVATD, String[] sumVATE, String[] sumVATF, String[] sumVATG, String[] sumVATH, int[] inv, String[] invNmb) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_sumVATA = new Variant();
		if( sumVATA == null || sumVATA.length == 0 )
			vnt_sumVATA.putNoParam();
		else
			vnt_sumVATA.putStringRef(sumVATA[0]);

		Variant vnt_sumVATB = new Variant();
		if( sumVATB == null || sumVATB.length == 0 )
			vnt_sumVATB.putNoParam();
		else
			vnt_sumVATB.putStringRef(sumVATB[0]);

		Variant vnt_sumVATC = new Variant();
		if( sumVATC == null || sumVATC.length == 0 )
			vnt_sumVATC.putNoParam();
		else
			vnt_sumVATC.putStringRef(sumVATC[0]);

		Variant vnt_sumVATD = new Variant();
		if( sumVATD == null || sumVATD.length == 0 )
			vnt_sumVATD.putNoParam();
		else
			vnt_sumVATD.putStringRef(sumVATD[0]);

		Variant vnt_sumVATE = new Variant();
		if( sumVATE == null || sumVATE.length == 0 )
			vnt_sumVATE.putNoParam();
		else
			vnt_sumVATE.putStringRef(sumVATE[0]);

		Variant vnt_sumVATF = new Variant();
		if( sumVATF == null || sumVATF.length == 0 )
			vnt_sumVATF.putNoParam();
		else
			vnt_sumVATF.putStringRef(sumVATF[0]);

		Variant vnt_sumVATG = new Variant();
		if( sumVATG == null || sumVATG.length == 0 )
			vnt_sumVATG.putNoParam();
		else
			vnt_sumVATG.putStringRef(sumVATG[0]);

		Variant vnt_sumVATH = new Variant();
		if( sumVATH == null || sumVATH.length == 0 )
			vnt_sumVATH.putNoParam();
		else
			vnt_sumVATH.putStringRef(sumVATH[0]);

		Variant vnt_inv = new Variant();
		if( inv == null || inv.length == 0 )
			vnt_inv.putNoParam();
		else
			vnt_inv.putIntRef(inv[0]);

		Variant vnt_invNmb = new Variant();
		if( invNmb == null || invNmb.length == 0 )
			vnt_invNmb.putNoParam();
		else
			vnt_invNmb.putStringRef(invNmb[0]);

		int result_of_command_103 = Dispatch.callN(this, "command_103", new Object[] { vnt_errorCode, vnt_sumVATA, vnt_sumVATB, vnt_sumVATC, vnt_sumVATD, vnt_sumVATE, vnt_sumVATF, vnt_sumVATG, vnt_sumVATH, vnt_inv, vnt_invNmb}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( sumVATA != null && sumVATA.length > 0 )
			sumVATA[0] = vnt_sumVATA.toString();
		if( sumVATB != null && sumVATB.length > 0 )
			sumVATB[0] = vnt_sumVATB.toString();
		if( sumVATC != null && sumVATC.length > 0 )
			sumVATC[0] = vnt_sumVATC.toString();
		if( sumVATD != null && sumVATD.length > 0 )
			sumVATD[0] = vnt_sumVATD.toString();
		if( sumVATE != null && sumVATE.length > 0 )
			sumVATE[0] = vnt_sumVATE.toString();
		if( sumVATF != null && sumVATF.length > 0 )
			sumVATF[0] = vnt_sumVATF.toString();
		if( sumVATG != null && sumVATG.length > 0 )
			sumVATG[0] = vnt_sumVATG.toString();
		if( sumVATH != null && sumVATH.length > 0 )
			sumVATH[0] = vnt_sumVATH.toString();
		if( inv != null && inv.length > 0 )
			inv[0] = vnt_inv.toInt();
		if( invNmb != null && invNmb.length > 0 )
			invNmb[0] = vnt_invNmb.toString();

		return result_of_command_103;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param firstOper an input-parameter of type int
	 * @param lastOper an input-parameter of type int
	 * @param clear an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_105(int firstOper, int lastOper, int clear, int errorCode) {
		return Dispatch.call(this, "command_105", new Variant(firstOper), new Variant(lastOper), new Variant(clear), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param firstOper an input-parameter of type int
	 * @param lastOper an input-parameter of type int
	 * @param clear an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_105(int firstOper, int lastOper, int clear, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_105 = Dispatch.call(this, "command_105", new Variant(firstOper), new Variant(lastOper), new Variant(clear), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_105;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param total an input-parameter of type int
	 * @param prog an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_107_v001(String option, int errorCode, int total, int prog) {
		return Dispatch.call(this, "command_107_v001", option, new Variant(errorCode), new Variant(total), new Variant(prog)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param prog is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v001(String option, int[] errorCode, int[] total, int[] prog) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putIntRef(total[0]);

		Variant vnt_prog = new Variant();
		if( prog == null || prog.length == 0 )
			vnt_prog.putNoParam();
		else
			vnt_prog.putIntRef(prog[0]);

		int result_of_command_107_v001 = Dispatch.call(this, "command_107_v001", option, vnt_errorCode, vnt_total, vnt_prog).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toInt();
		if( prog != null && prog.length > 0 )
			prog[0] = vnt_prog.toInt();

		return result_of_command_107_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param pLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param dep an input-parameter of type int
	 * @param group an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param addQty an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_107_v002(String option, int pLU, String taxGr, int dep, int group, int priceType, String price, String addQty, String quantity, String bar1, String bar2, String bar3, String bar4, String itemName, int errorCode) {
		return Dispatch.callN(this, "command_107_v002", new Object[] { option, new Variant(pLU), taxGr, new Variant(dep), new Variant(group), new Variant(priceType), price, addQty, quantity, bar1, bar2, bar3, bar4, itemName, new Variant(errorCode)}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param pLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param dep an input-parameter of type int
	 * @param group an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param addQty an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v002(String option, int pLU, String taxGr, int dep, int group, int priceType, String price, String addQty, String quantity, String bar1, String bar2, String bar3, String bar4, String itemName, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_107_v002 = Dispatch.callN(this, "command_107_v002", new Object[] { option, new Variant(pLU), taxGr, new Variant(dep), new Variant(group), new Variant(priceType), price, addQty, quantity, bar1, bar2, bar3, bar4, itemName, vnt_errorCode}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_107_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param pLU an input-parameter of type int
	 * @param quantity an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_107_v003(String option, int pLU, String quantity, int errorCode) {
		return Dispatch.call(this, "command_107_v003", option, new Variant(pLU), quantity, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param pLU an input-parameter of type int
	 * @param quantity an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v003(String option, int pLU, String quantity, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_107_v003 = Dispatch.call(this, "command_107_v003", option, new Variant(pLU), quantity, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_107_v003;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startPLU an input-parameter of type int
	 * @param endPLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_107_v004(String option, int startPLU, int endPLU, int errorCode) {
		return Dispatch.call(this, "command_107_v004", option, new Variant(startPLU), new Variant(endPLU), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startPLU an input-parameter of type int
	 * @param endPLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v004(String option, int startPLU, int endPLU, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_107_v004 = Dispatch.call(this, "command_107_v004", option, new Variant(startPLU), new Variant(endPLU), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_107_v004;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param deleteAll an input-parameter of type String
	 * @param lastPLU an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_107_v005(String option, String deleteAll, String lastPLU, int errorCode) {
		return Dispatch.call(this, "command_107_v005", option, deleteAll, lastPLU, new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param deleteAll an input-parameter of type String
	 * @param lastPLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v005(String option, String deleteAll, String lastPLU, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_107_v005 = Dispatch.call(this, "command_107_v005", option, deleteAll, lastPLU, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_107_v005;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v006(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v006", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v006(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v006 = Dispatch.callN(this, "command_107_v006", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v006;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v007(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v007", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v007(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v007 = Dispatch.callN(this, "command_107_v007", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v007;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v008(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v008", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v008(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v008 = Dispatch.callN(this, "command_107_v008", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v008;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param pLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v009(String option, int errorCode, int pLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v009", new Object[] { option, new Variant(errorCode), new Variant(pLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param pLU is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v009(String option, int[] errorCode, int[] pLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_pLU = new Variant();
		if( pLU == null || pLU.length == 0 )
			vnt_pLU.putNoParam();
		else
			vnt_pLU.putIntRef(pLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v009 = Dispatch.callN(this, "command_107_v009", new Object[] { option, vnt_errorCode, vnt_pLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( pLU != null && pLU.length > 0 )
			pLU[0] = vnt_pLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v009;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v010(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v010", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v010(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v010 = Dispatch.callN(this, "command_107_v010", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v010;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v011(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v011", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v011(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v011 = Dispatch.callN(this, "command_107_v011", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v011;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode an input-parameter of type int
	 * @param pLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v012(String option, int errorCode, int pLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v012", new Object[] { option, new Variant(errorCode), new Variant(pLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param pLU is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v012(String option, int[] errorCode, int[] pLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_pLU = new Variant();
		if( pLU == null || pLU.length == 0 )
			vnt_pLU.putNoParam();
		else
			vnt_pLU.putIntRef(pLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v012 = Dispatch.callN(this, "command_107_v012", new Object[] { option, vnt_errorCode, vnt_pLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( pLU != null && pLU.length > 0 )
			pLU[0] = vnt_pLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v012;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v013(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v013", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v013(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v013 = Dispatch.callN(this, "command_107_v013", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v013;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_PLU an input-parameter of type int
	 * @param taxGr an input-parameter of type String
	 * @param department an input-parameter of type int
	 * @param stockGroup an input-parameter of type int
	 * @param priceType an input-parameter of type int
	 * @param price an input-parameter of type String
	 * @param turnover an input-parameter of type String
	 * @param soldQty an input-parameter of type String
	 * @param stockQty an input-parameter of type String
	 * @param bar1 an input-parameter of type String
	 * @param bar2 an input-parameter of type String
	 * @param bar3 an input-parameter of type String
	 * @param bar4 an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_107_v014(String option, int in_PLU, int errorCode, int out_PLU, String taxGr, int department, int stockGroup, int priceType, String price, String turnover, String soldQty, String stockQty, String bar1, String bar2, String bar3, String bar4, String itemName) {
		return Dispatch.callN(this, "command_107_v014", new Object[] { option, new Variant(in_PLU), new Variant(errorCode), new Variant(out_PLU), taxGr, new Variant(department), new Variant(stockGroup), new Variant(priceType), price, turnover, soldQty, stockQty, bar1, bar2, bar3, bar4, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param in_PLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_PLU is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param department is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param stockGroup is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param priceType is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param price is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param turnover is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param soldQty is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param stockQty is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param bar1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param bar4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_107_v014(String option, int in_PLU, int[] errorCode, int[] out_PLU, String[] taxGr, int[] department, int[] stockGroup, int[] priceType, String[] price, String[] turnover, String[] soldQty, String[] stockQty, String[] bar1, String[] bar2, String[] bar3, String[] bar4, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_PLU = new Variant();
		if( out_PLU == null || out_PLU.length == 0 )
			vnt_out_PLU.putNoParam();
		else
			vnt_out_PLU.putIntRef(out_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_department = new Variant();
		if( department == null || department.length == 0 )
			vnt_department.putNoParam();
		else
			vnt_department.putIntRef(department[0]);

		Variant vnt_stockGroup = new Variant();
		if( stockGroup == null || stockGroup.length == 0 )
			vnt_stockGroup.putNoParam();
		else
			vnt_stockGroup.putIntRef(stockGroup[0]);

		Variant vnt_priceType = new Variant();
		if( priceType == null || priceType.length == 0 )
			vnt_priceType.putNoParam();
		else
			vnt_priceType.putIntRef(priceType[0]);

		Variant vnt_price = new Variant();
		if( price == null || price.length == 0 )
			vnt_price.putNoParam();
		else
			vnt_price.putStringRef(price[0]);

		Variant vnt_turnover = new Variant();
		if( turnover == null || turnover.length == 0 )
			vnt_turnover.putNoParam();
		else
			vnt_turnover.putStringRef(turnover[0]);

		Variant vnt_soldQty = new Variant();
		if( soldQty == null || soldQty.length == 0 )
			vnt_soldQty.putNoParam();
		else
			vnt_soldQty.putStringRef(soldQty[0]);

		Variant vnt_stockQty = new Variant();
		if( stockQty == null || stockQty.length == 0 )
			vnt_stockQty.putNoParam();
		else
			vnt_stockQty.putStringRef(stockQty[0]);

		Variant vnt_bar1 = new Variant();
		if( bar1 == null || bar1.length == 0 )
			vnt_bar1.putNoParam();
		else
			vnt_bar1.putStringRef(bar1[0]);

		Variant vnt_bar2 = new Variant();
		if( bar2 == null || bar2.length == 0 )
			vnt_bar2.putNoParam();
		else
			vnt_bar2.putStringRef(bar2[0]);

		Variant vnt_bar3 = new Variant();
		if( bar3 == null || bar3.length == 0 )
			vnt_bar3.putNoParam();
		else
			vnt_bar3.putStringRef(bar3[0]);

		Variant vnt_bar4 = new Variant();
		if( bar4 == null || bar4.length == 0 )
			vnt_bar4.putNoParam();
		else
			vnt_bar4.putStringRef(bar4[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_command_107_v014 = Dispatch.callN(this, "command_107_v014", new Object[] { option, new Variant(in_PLU), vnt_errorCode, vnt_out_PLU, vnt_taxGr, vnt_department, vnt_stockGroup, vnt_priceType, vnt_price, vnt_turnover, vnt_soldQty, vnt_stockQty, vnt_bar1, vnt_bar2, vnt_bar3, vnt_bar4, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_PLU != null && out_PLU.length > 0 )
			out_PLU[0] = vnt_out_PLU.toInt();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( department != null && department.length > 0 )
			department[0] = vnt_department.toInt();
		if( stockGroup != null && stockGroup.length > 0 )
			stockGroup[0] = vnt_stockGroup.toInt();
		if( priceType != null && priceType.length > 0 )
			priceType[0] = vnt_priceType.toInt();
		if( price != null && price.length > 0 )
			price[0] = vnt_price.toString();
		if( turnover != null && turnover.length > 0 )
			turnover[0] = vnt_turnover.toString();
		if( soldQty != null && soldQty.length > 0 )
			soldQty[0] = vnt_soldQty.toString();
		if( stockQty != null && stockQty.length > 0 )
			stockQty[0] = vnt_stockQty.toString();
		if( bar1 != null && bar1.length > 0 )
			bar1[0] = vnt_bar1.toString();
		if( bar2 != null && bar2.length > 0 )
			bar2[0] = vnt_bar2.toString();
		if( bar3 != null && bar3.length > 0 )
			bar3[0] = vnt_bar3.toString();
		if( bar4 != null && bar4.length > 0 )
			bar4[0] = vnt_bar4.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_command_107_v014;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_109(int errorCode) {
		return Dispatch.call(this, "command_109", new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_109(int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_109 = Dispatch.call(this, "command_109", vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_109;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param pay1 an input-parameter of type String
	 * @param pay2 an input-parameter of type String
	 * @param pay3 an input-parameter of type String
	 * @param pay4 an input-parameter of type String
	 * @param pay5 an input-parameter of type String
	 * @param pay6 an input-parameter of type String
	 * @param foreignPay an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v001(int reportType, int errorCode, String pay1, String pay2, String pay3, String pay4, String pay5, String pay6, String foreignPay) {
		return Dispatch.callN(this, "command_110_v001", new Object[] { new Variant(reportType), new Variant(errorCode), pay1, pay2, pay3, pay4, pay5, pay6, foreignPay}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param pay1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay5 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay6 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param foreignPay is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v001(int reportType, int[] errorCode, String[] pay1, String[] pay2, String[] pay3, String[] pay4, String[] pay5, String[] pay6, String[] foreignPay) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_pay1 = new Variant();
		if( pay1 == null || pay1.length == 0 )
			vnt_pay1.putNoParam();
		else
			vnt_pay1.putStringRef(pay1[0]);

		Variant vnt_pay2 = new Variant();
		if( pay2 == null || pay2.length == 0 )
			vnt_pay2.putNoParam();
		else
			vnt_pay2.putStringRef(pay2[0]);

		Variant vnt_pay3 = new Variant();
		if( pay3 == null || pay3.length == 0 )
			vnt_pay3.putNoParam();
		else
			vnt_pay3.putStringRef(pay3[0]);

		Variant vnt_pay4 = new Variant();
		if( pay4 == null || pay4.length == 0 )
			vnt_pay4.putNoParam();
		else
			vnt_pay4.putStringRef(pay4[0]);

		Variant vnt_pay5 = new Variant();
		if( pay5 == null || pay5.length == 0 )
			vnt_pay5.putNoParam();
		else
			vnt_pay5.putStringRef(pay5[0]);

		Variant vnt_pay6 = new Variant();
		if( pay6 == null || pay6.length == 0 )
			vnt_pay6.putNoParam();
		else
			vnt_pay6.putStringRef(pay6[0]);

		Variant vnt_foreignPay = new Variant();
		if( foreignPay == null || foreignPay.length == 0 )
			vnt_foreignPay.putNoParam();
		else
			vnt_foreignPay.putStringRef(foreignPay[0]);

		int result_of_command_110_v001 = Dispatch.callN(this, "command_110_v001", new Object[] { new Variant(reportType), vnt_errorCode, vnt_pay1, vnt_pay2, vnt_pay3, vnt_pay4, vnt_pay5, vnt_pay6, vnt_foreignPay}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( pay1 != null && pay1.length > 0 )
			pay1[0] = vnt_pay1.toString();
		if( pay2 != null && pay2.length > 0 )
			pay2[0] = vnt_pay2.toString();
		if( pay3 != null && pay3.length > 0 )
			pay3[0] = vnt_pay3.toString();
		if( pay4 != null && pay4.length > 0 )
			pay4[0] = vnt_pay4.toString();
		if( pay5 != null && pay5.length > 0 )
			pay5[0] = vnt_pay5.toString();
		if( pay6 != null && pay6.length > 0 )
			pay6[0] = vnt_pay6.toString();
		if( foreignPay != null && foreignPay.length > 0 )
			foreignPay[0] = vnt_foreignPay.toString();

		return result_of_command_110_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param pay1 an input-parameter of type String
	 * @param pay2 an input-parameter of type String
	 * @param pay3 an input-parameter of type String
	 * @param pay4 an input-parameter of type String
	 * @param pay5 an input-parameter of type String
	 * @param pay6 an input-parameter of type String
	 * @param foreignPay an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v002(int reportType, int errorCode, String pay1, String pay2, String pay3, String pay4, String pay5, String pay6, String foreignPay) {
		return Dispatch.callN(this, "command_110_v002", new Object[] { new Variant(reportType), new Variant(errorCode), pay1, pay2, pay3, pay4, pay5, pay6, foreignPay}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param pay1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay5 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay6 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param foreignPay is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v002(int reportType, int[] errorCode, String[] pay1, String[] pay2, String[] pay3, String[] pay4, String[] pay5, String[] pay6, String[] foreignPay) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_pay1 = new Variant();
		if( pay1 == null || pay1.length == 0 )
			vnt_pay1.putNoParam();
		else
			vnt_pay1.putStringRef(pay1[0]);

		Variant vnt_pay2 = new Variant();
		if( pay2 == null || pay2.length == 0 )
			vnt_pay2.putNoParam();
		else
			vnt_pay2.putStringRef(pay2[0]);

		Variant vnt_pay3 = new Variant();
		if( pay3 == null || pay3.length == 0 )
			vnt_pay3.putNoParam();
		else
			vnt_pay3.putStringRef(pay3[0]);

		Variant vnt_pay4 = new Variant();
		if( pay4 == null || pay4.length == 0 )
			vnt_pay4.putNoParam();
		else
			vnt_pay4.putStringRef(pay4[0]);

		Variant vnt_pay5 = new Variant();
		if( pay5 == null || pay5.length == 0 )
			vnt_pay5.putNoParam();
		else
			vnt_pay5.putStringRef(pay5[0]);

		Variant vnt_pay6 = new Variant();
		if( pay6 == null || pay6.length == 0 )
			vnt_pay6.putNoParam();
		else
			vnt_pay6.putStringRef(pay6[0]);

		Variant vnt_foreignPay = new Variant();
		if( foreignPay == null || foreignPay.length == 0 )
			vnt_foreignPay.putNoParam();
		else
			vnt_foreignPay.putStringRef(foreignPay[0]);

		int result_of_command_110_v002 = Dispatch.callN(this, "command_110_v002", new Object[] { new Variant(reportType), vnt_errorCode, vnt_pay1, vnt_pay2, vnt_pay3, vnt_pay4, vnt_pay5, vnt_pay6, vnt_foreignPay}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( pay1 != null && pay1.length > 0 )
			pay1[0] = vnt_pay1.toString();
		if( pay2 != null && pay2.length > 0 )
			pay2[0] = vnt_pay2.toString();
		if( pay3 != null && pay3.length > 0 )
			pay3[0] = vnt_pay3.toString();
		if( pay4 != null && pay4.length > 0 )
			pay4[0] = vnt_pay4.toString();
		if( pay5 != null && pay5.length > 0 )
			pay5[0] = vnt_pay5.toString();
		if( pay6 != null && pay6.length > 0 )
			pay6[0] = vnt_pay6.toString();
		if( foreignPay != null && foreignPay.length > 0 )
			foreignPay[0] = vnt_foreignPay.toString();

		return result_of_command_110_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param num an input-parameter of type String
	 * @param sum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v003(int reportType, int errorCode, String num, String sum) {
		return Dispatch.call(this, "command_110_v003", new Variant(reportType), new Variant(errorCode), num, sum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param num is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param sum is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v003(int reportType, int[] errorCode, String[] num, String[] sum) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_num = new Variant();
		if( num == null || num.length == 0 )
			vnt_num.putNoParam();
		else
			vnt_num.putStringRef(num[0]);

		Variant vnt_sum = new Variant();
		if( sum == null || sum.length == 0 )
			vnt_sum.putNoParam();
		else
			vnt_sum.putStringRef(sum[0]);

		int result_of_command_110_v003 = Dispatch.call(this, "command_110_v003", new Variant(reportType), vnt_errorCode, vnt_num, vnt_sum).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( num != null && num.length > 0 )
			num[0] = vnt_num.toString();
		if( sum != null && sum.length > 0 )
			sum[0] = vnt_sum.toString();

		return result_of_command_110_v003;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param qSur an input-parameter of type String
	 * @param sSur an input-parameter of type String
	 * @param qDis an input-parameter of type String
	 * @param sDis an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v004(int reportType, int errorCode, String qSur, String sSur, String qDis, String sDis) {
		return Dispatch.call(this, "command_110_v004", new Variant(reportType), new Variant(errorCode), qSur, sSur, qDis, sDis).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param qSur is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sSur is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param qDis is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param sDis is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v004(int reportType, int[] errorCode, String[] qSur, String[] sSur, String[] qDis, String[] sDis) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_qSur = new Variant();
		if( qSur == null || qSur.length == 0 )
			vnt_qSur.putNoParam();
		else
			vnt_qSur.putStringRef(qSur[0]);

		Variant vnt_sSur = new Variant();
		if( sSur == null || sSur.length == 0 )
			vnt_sSur.putNoParam();
		else
			vnt_sSur.putStringRef(sSur[0]);

		Variant vnt_qDis = new Variant();
		if( qDis == null || qDis.length == 0 )
			vnt_qDis.putNoParam();
		else
			vnt_qDis.putStringRef(qDis[0]);

		Variant vnt_sDis = new Variant();
		if( sDis == null || sDis.length == 0 )
			vnt_sDis.putNoParam();
		else
			vnt_sDis.putStringRef(sDis[0]);

		int result_of_command_110_v004 = Dispatch.call(this, "command_110_v004", new Variant(reportType), vnt_errorCode, vnt_qSur, vnt_sSur, vnt_qDis, vnt_sDis).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( qSur != null && qSur.length > 0 )
			qSur[0] = vnt_qSur.toString();
		if( sSur != null && sSur.length > 0 )
			sSur[0] = vnt_sSur.toString();
		if( qDis != null && qDis.length > 0 )
			qDis[0] = vnt_qDis.toString();
		if( sDis != null && sDis.length > 0 )
			sDis[0] = vnt_sDis.toString();

		return result_of_command_110_v004;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param qVoid an input-parameter of type String
	 * @param sVoid an input-parameter of type String
	 * @param qAnul an input-parameter of type String
	 * @param sAnul an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v005(int reportType, int errorCode, String qVoid, String sVoid, String qAnul, String sAnul) {
		return Dispatch.call(this, "command_110_v005", new Variant(reportType), new Variant(errorCode), qVoid, sVoid, qAnul, sAnul).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param qVoid is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sVoid is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param qAnul is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sAnul is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v005(int reportType, int[] errorCode, String[] qVoid, String[] sVoid, String[] qAnul, String[] sAnul) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_qVoid = new Variant();
		if( qVoid == null || qVoid.length == 0 )
			vnt_qVoid.putNoParam();
		else
			vnt_qVoid.putStringRef(qVoid[0]);

		Variant vnt_sVoid = new Variant();
		if( sVoid == null || sVoid.length == 0 )
			vnt_sVoid.putNoParam();
		else
			vnt_sVoid.putStringRef(sVoid[0]);

		Variant vnt_qAnul = new Variant();
		if( qAnul == null || qAnul.length == 0 )
			vnt_qAnul.putNoParam();
		else
			vnt_qAnul.putStringRef(qAnul[0]);

		Variant vnt_sAnul = new Variant();
		if( sAnul == null || sAnul.length == 0 )
			vnt_sAnul.putNoParam();
		else
			vnt_sAnul.putStringRef(sAnul[0]);

		int result_of_command_110_v005 = Dispatch.call(this, "command_110_v005", new Variant(reportType), vnt_errorCode, vnt_qVoid, vnt_sVoid, vnt_qAnul, vnt_sAnul).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( qVoid != null && qVoid.length > 0 )
			qVoid[0] = vnt_qVoid.toString();
		if( sVoid != null && sVoid.length > 0 )
			sVoid[0] = vnt_sVoid.toString();
		if( qAnul != null && qAnul.length > 0 )
			qAnul[0] = vnt_qAnul.toString();
		if( sAnul != null && sAnul.length > 0 )
			sAnul[0] = vnt_sAnul.toString();

		return result_of_command_110_v005;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param qCashIn1 an input-parameter of type String
	 * @param sCashIn1 an input-parameter of type String
	 * @param qCashOut1 an input-parameter of type String
	 * @param sCashOut1 an input-parameter of type String
	 * @param qCashIn2 an input-parameter of type String
	 * @param sCashIn2 an input-parameter of type String
	 * @param qCashOut2 an input-parameter of type String
	 * @param sCashOut2 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_110_v006(int reportType, int errorCode, String qCashIn1, String sCashIn1, String qCashOut1, String sCashOut1, String qCashIn2, String sCashIn2, String qCashOut2, String sCashOut2) {
		return Dispatch.callN(this, "command_110_v006", new Object[] { new Variant(reportType), new Variant(errorCode), qCashIn1, sCashIn1, qCashOut1, sCashOut1, qCashIn2, sCashIn2, qCashOut2, sCashOut2}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param qCashIn1 is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param sCashIn1 is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param qCashOut1 is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param sCashOut1 is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param qCashIn2 is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param sCashIn2 is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param qCashOut2 is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param sCashOut2 is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_110_v006(int reportType, int[] errorCode, String[] qCashIn1, String[] sCashIn1, String[] qCashOut1, String[] sCashOut1, String[] qCashIn2, String[] sCashIn2, String[] qCashOut2, String[] sCashOut2) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_qCashIn1 = new Variant();
		if( qCashIn1 == null || qCashIn1.length == 0 )
			vnt_qCashIn1.putNoParam();
		else
			vnt_qCashIn1.putStringRef(qCashIn1[0]);

		Variant vnt_sCashIn1 = new Variant();
		if( sCashIn1 == null || sCashIn1.length == 0 )
			vnt_sCashIn1.putNoParam();
		else
			vnt_sCashIn1.putStringRef(sCashIn1[0]);

		Variant vnt_qCashOut1 = new Variant();
		if( qCashOut1 == null || qCashOut1.length == 0 )
			vnt_qCashOut1.putNoParam();
		else
			vnt_qCashOut1.putStringRef(qCashOut1[0]);

		Variant vnt_sCashOut1 = new Variant();
		if( sCashOut1 == null || sCashOut1.length == 0 )
			vnt_sCashOut1.putNoParam();
		else
			vnt_sCashOut1.putStringRef(sCashOut1[0]);

		Variant vnt_qCashIn2 = new Variant();
		if( qCashIn2 == null || qCashIn2.length == 0 )
			vnt_qCashIn2.putNoParam();
		else
			vnt_qCashIn2.putStringRef(qCashIn2[0]);

		Variant vnt_sCashIn2 = new Variant();
		if( sCashIn2 == null || sCashIn2.length == 0 )
			vnt_sCashIn2.putNoParam();
		else
			vnt_sCashIn2.putStringRef(sCashIn2[0]);

		Variant vnt_qCashOut2 = new Variant();
		if( qCashOut2 == null || qCashOut2.length == 0 )
			vnt_qCashOut2.putNoParam();
		else
			vnt_qCashOut2.putStringRef(qCashOut2[0]);

		Variant vnt_sCashOut2 = new Variant();
		if( sCashOut2 == null || sCashOut2.length == 0 )
			vnt_sCashOut2.putNoParam();
		else
			vnt_sCashOut2.putStringRef(sCashOut2[0]);

		int result_of_command_110_v006 = Dispatch.callN(this, "command_110_v006", new Object[] { new Variant(reportType), vnt_errorCode, vnt_qCashIn1, vnt_sCashIn1, vnt_qCashOut1, vnt_sCashOut1, vnt_qCashIn2, vnt_sCashIn2, vnt_qCashOut2, vnt_sCashOut2}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( qCashIn1 != null && qCashIn1.length > 0 )
			qCashIn1[0] = vnt_qCashIn1.toString();
		if( sCashIn1 != null && sCashIn1.length > 0 )
			sCashIn1[0] = vnt_sCashIn1.toString();
		if( qCashOut1 != null && qCashOut1.length > 0 )
			qCashOut1[0] = vnt_qCashOut1.toString();
		if( sCashOut1 != null && sCashOut1.length > 0 )
			sCashOut1[0] = vnt_sCashOut1.toString();
		if( qCashIn2 != null && qCashIn2.length > 0 )
			qCashIn2[0] = vnt_qCashIn2.toString();
		if( sCashIn2 != null && sCashIn2.length > 0 )
			sCashIn2[0] = vnt_sCashIn2.toString();
		if( qCashOut2 != null && qCashOut2.length > 0 )
			qCashOut2[0] = vnt_qCashOut2.toString();
		if( sCashOut2 != null && sCashOut2.length > 0 )
			sCashOut2[0] = vnt_sCashOut2.toString();

		return result_of_command_110_v006;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param startPLU an input-parameter of type int
	 * @param endPLU an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_111(int reportType, int startPLU, int endPLU, int errorCode) {
		return Dispatch.call(this, "command_111", new Variant(reportType), new Variant(startPLU), new Variant(endPLU), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportType an input-parameter of type int
	 * @param startPLU an input-parameter of type int
	 * @param endPLU an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_111(int reportType, int startPLU, int endPLU, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_111 = Dispatch.call(this, "command_111", new Variant(reportType), new Variant(startPLU), new Variant(endPLU), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_111;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_Operator an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param receipts an input-parameter of type int
	 * @param total an input-parameter of type String
	 * @param nDiscount an input-parameter of type int
	 * @param discount an input-parameter of type String
	 * @param nSurcharge an input-parameter of type int
	 * @param surcharge an input-parameter of type String
	 * @param nVoid an input-parameter of type int
	 * @param void_ an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_112(int in_Operator, int errorCode, int receipts, String total, int nDiscount, String discount, int nSurcharge, String surcharge, int nVoid, String void_) {
		return Dispatch.callN(this, "command_112", new Object[] { new Variant(in_Operator), new Variant(errorCode), new Variant(receipts), total, new Variant(nDiscount), discount, new Variant(nSurcharge), surcharge, new Variant(nVoid), void_}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_Operator an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param nDiscount is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param discount is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param nSurcharge is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param surcharge is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param nVoid is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param void_ is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_112(int in_Operator, int[] errorCode, int[] receipts, String[] total, int[] nDiscount, String[] discount, int[] nSurcharge, String[] surcharge, int[] nVoid, String[] void_) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putIntRef(receipts[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_nDiscount = new Variant();
		if( nDiscount == null || nDiscount.length == 0 )
			vnt_nDiscount.putNoParam();
		else
			vnt_nDiscount.putIntRef(nDiscount[0]);

		Variant vnt_discount = new Variant();
		if( discount == null || discount.length == 0 )
			vnt_discount.putNoParam();
		else
			vnt_discount.putStringRef(discount[0]);

		Variant vnt_nSurcharge = new Variant();
		if( nSurcharge == null || nSurcharge.length == 0 )
			vnt_nSurcharge.putNoParam();
		else
			vnt_nSurcharge.putIntRef(nSurcharge[0]);

		Variant vnt_surcharge = new Variant();
		if( surcharge == null || surcharge.length == 0 )
			vnt_surcharge.putNoParam();
		else
			vnt_surcharge.putStringRef(surcharge[0]);

		Variant vnt_nVoid = new Variant();
		if( nVoid == null || nVoid.length == 0 )
			vnt_nVoid.putNoParam();
		else
			vnt_nVoid.putIntRef(nVoid[0]);

		Variant vnt_void = new Variant();
		if( void_ == null || void_.length == 0 )
			vnt_void.putNoParam();
		else
			vnt_void.putStringRef(void_[0]);

		int result_of_command_112 = Dispatch.callN(this, "command_112", new Object[] { new Variant(in_Operator), vnt_errorCode, vnt_receipts, vnt_total, vnt_nDiscount, vnt_discount, vnt_nSurcharge, vnt_surcharge, vnt_nVoid, vnt_void}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toInt();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( nDiscount != null && nDiscount.length > 0 )
			nDiscount[0] = vnt_nDiscount.toInt();
		if( discount != null && discount.length > 0 )
			discount[0] = vnt_discount.toString();
		if( nSurcharge != null && nSurcharge.length > 0 )
			nSurcharge[0] = vnt_nSurcharge.toInt();
		if( surcharge != null && surcharge.length > 0 )
			surcharge[0] = vnt_surcharge.toString();
		if( nVoid != null && nVoid.length > 0 )
			nVoid[0] = vnt_nVoid.toInt();
		if( void_ != null && void_.length > 0 )
			void_[0] = vnt_void.toString();

		return result_of_command_112;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operation an input-parameter of type int
	 * @param address an input-parameter of type String
	 * @param nBytes an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param data an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_116(int operation, String address, int nBytes, int errorCode, String data) {
		return Dispatch.call(this, "command_116", new Variant(operation), address, new Variant(nBytes), new Variant(errorCode), data).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operation an input-parameter of type int
	 * @param address an input-parameter of type String
	 * @param nBytes an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param data is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_116(int operation, String address, int nBytes, int[] errorCode, String[] data) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_data = new Variant();
		if( data == null || data.length == 0 )
			vnt_data.putNoParam();
		else
			vnt_data.putStringRef(data[0]);

		int result_of_command_116 = Dispatch.call(this, "command_116", new Variant(operation), address, new Variant(nBytes), vnt_errorCode, vnt_data).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( data != null && data.length > 0 )
			data[0] = vnt_data.toString();

		return result_of_command_116;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param in_StartDate an input-parameter of type String
	 * @param in_EndDate an input-parameter of type String
	 * @param in_Type an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param out_StartDate an input-parameter of type String
	 * @param out_EndDate an input-parameter of type String
	 * @param firstDoc an input-parameter of type int
	 * @param lastDoc an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_124(String in_StartDate, String in_EndDate, int in_Type, int errorCode, String out_StartDate, String out_EndDate, int firstDoc, int lastDoc) {
		return Dispatch.call(this, "command_124", in_StartDate, in_EndDate, new Variant(in_Type), new Variant(errorCode), out_StartDate, out_EndDate, new Variant(firstDoc), new Variant(lastDoc)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param in_StartDate an input-parameter of type String
	 * @param in_EndDate an input-parameter of type String
	 * @param in_Type an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param out_StartDate is an one-element array which sends the input-parameter
	 *                      to the ActiveX-Component and receives the output-parameter
	 * @param out_EndDate is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param firstDoc is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param lastDoc is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_124(String in_StartDate, String in_EndDate, int in_Type, int[] errorCode, String[] out_StartDate, String[] out_EndDate, int[] firstDoc, int[] lastDoc) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_out_StartDate = new Variant();
		if( out_StartDate == null || out_StartDate.length == 0 )
			vnt_out_StartDate.putNoParam();
		else
			vnt_out_StartDate.putStringRef(out_StartDate[0]);

		Variant vnt_out_EndDate = new Variant();
		if( out_EndDate == null || out_EndDate.length == 0 )
			vnt_out_EndDate.putNoParam();
		else
			vnt_out_EndDate.putStringRef(out_EndDate[0]);

		Variant vnt_firstDoc = new Variant();
		if( firstDoc == null || firstDoc.length == 0 )
			vnt_firstDoc.putNoParam();
		else
			vnt_firstDoc.putIntRef(firstDoc[0]);

		Variant vnt_lastDoc = new Variant();
		if( lastDoc == null || lastDoc.length == 0 )
			vnt_lastDoc.putNoParam();
		else
			vnt_lastDoc.putIntRef(lastDoc[0]);

		int result_of_command_124 = Dispatch.call(this, "command_124", in_StartDate, in_EndDate, new Variant(in_Type), vnt_errorCode, vnt_out_StartDate, vnt_out_EndDate, vnt_firstDoc, vnt_lastDoc).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( out_StartDate != null && out_StartDate.length > 0 )
			out_StartDate[0] = vnt_out_StartDate.toString();
		if( out_EndDate != null && out_EndDate.length > 0 )
			out_EndDate[0] = vnt_out_EndDate.toString();
		if( firstDoc != null && firstDoc.length > 0 )
			firstDoc[0] = vnt_firstDoc.toInt();
		if( lastDoc != null && lastDoc.length > 0 )
			lastDoc[0] = vnt_lastDoc.toInt();

		return result_of_command_124;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param docNumber an input-parameter of type int
	 * @param recNumber an input-parameter of type int
	 * @param date an input-parameter of type String
	 * @param out_Type an input-parameter of type int
	 * @param znumber an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_125_v001(int option, int docNum, int recType, int errorCode, int docNumber, int recNumber, String date, int out_Type, int znumber) {
		return Dispatch.callN(this, "command_125_v001", new Object[] { new Variant(option), new Variant(docNum), new Variant(recType), new Variant(errorCode), new Variant(docNumber), new Variant(recNumber), date, new Variant(out_Type), new Variant(znumber)}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param docNumber is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param recNumber is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param date is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param out_Type is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param znumber is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_125_v001(int option, int docNum, int recType, int[] errorCode, int[] docNumber, int[] recNumber, String[] date, int[] out_Type, int[] znumber) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_docNumber = new Variant();
		if( docNumber == null || docNumber.length == 0 )
			vnt_docNumber.putNoParam();
		else
			vnt_docNumber.putIntRef(docNumber[0]);

		Variant vnt_recNumber = new Variant();
		if( recNumber == null || recNumber.length == 0 )
			vnt_recNumber.putNoParam();
		else
			vnt_recNumber.putIntRef(recNumber[0]);

		Variant vnt_date = new Variant();
		if( date == null || date.length == 0 )
			vnt_date.putNoParam();
		else
			vnt_date.putStringRef(date[0]);

		Variant vnt_out_Type = new Variant();
		if( out_Type == null || out_Type.length == 0 )
			vnt_out_Type.putNoParam();
		else
			vnt_out_Type.putIntRef(out_Type[0]);

		Variant vnt_znumber = new Variant();
		if( znumber == null || znumber.length == 0 )
			vnt_znumber.putNoParam();
		else
			vnt_znumber.putIntRef(znumber[0]);

		int result_of_command_125_v001 = Dispatch.callN(this, "command_125_v001", new Object[] { new Variant(option), new Variant(docNum), new Variant(recType), vnt_errorCode, vnt_docNumber, vnt_recNumber, vnt_date, vnt_out_Type, vnt_znumber}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( docNumber != null && docNumber.length > 0 )
			docNumber[0] = vnt_docNumber.toInt();
		if( recNumber != null && recNumber.length > 0 )
			recNumber[0] = vnt_recNumber.toInt();
		if( date != null && date.length > 0 )
			date[0] = vnt_date.toString();
		if( out_Type != null && out_Type.length > 0 )
			out_Type[0] = vnt_out_Type.toInt();
		if( znumber != null && znumber.length > 0 )
			znumber[0] = vnt_znumber.toInt();

		return result_of_command_125_v001;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param textData an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_125_v002(int option, int docNum, int recType, int errorCode, String textData) {
		return Dispatch.call(this, "command_125_v002", new Variant(option), new Variant(docNum), new Variant(recType), new Variant(errorCode), textData).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param textData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_125_v002(int option, int docNum, int recType, int[] errorCode, String[] textData) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_textData = new Variant();
		if( textData == null || textData.length == 0 )
			vnt_textData.putNoParam();
		else
			vnt_textData.putStringRef(textData[0]);

		int result_of_command_125_v002 = Dispatch.call(this, "command_125_v002", new Variant(option), new Variant(docNum), new Variant(recType), vnt_errorCode, vnt_textData).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( textData != null && textData.length > 0 )
			textData[0] = vnt_textData.toString();

		return result_of_command_125_v002;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @param data an input-parameter of type String
	 * @return the result is of type int
	 */
	public int command_125_v003(int option, int docNum, int recType, int errorCode, String data) {
		return Dispatch.call(this, "command_125_v003", new Variant(option), new Variant(docNum), new Variant(recType), new Variant(errorCode), data).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param data is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_125_v003(int option, int docNum, int recType, int[] errorCode, String[] data) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		Variant vnt_data = new Variant();
		if( data == null || data.length == 0 )
			vnt_data.putNoParam();
		else
			vnt_data.putStringRef(data[0]);

		int result_of_command_125_v003 = Dispatch.call(this, "command_125_v003", new Variant(option), new Variant(docNum), new Variant(recType), vnt_errorCode, vnt_data).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();
		if( data != null && data.length > 0 )
			data[0] = vnt_data.toString();

		return result_of_command_125_v003;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode an input-parameter of type int
	 * @return the result is of type int
	 */
	public int command_125_v004(int option, int docNum, int recType, int errorCode) {
		return Dispatch.call(this, "command_125_v004", new Variant(option), new Variant(docNum), new Variant(recType), new Variant(errorCode)).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type int
	 * @param docNum an input-parameter of type int
	 * @param recType an input-parameter of type int
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int command_125_v004(int option, int docNum, int recType, int[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putIntRef(errorCode[0]);

		int result_of_command_125_v004 = Dispatch.call(this, "command_125_v004", new Variant(option), new Variant(docNum), new Variant(recType), vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toInt();

		return result_of_command_125_v004;
	}

}
