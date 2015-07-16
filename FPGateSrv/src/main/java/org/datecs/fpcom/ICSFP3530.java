/**
 * JacobGen generated file --- do not edit
 *
 * (http://www.sourceforge.net/projects/jacob-project */
package org.datecs.fpcom;

import com.jacob.com.*;

public class ICSFP3530 extends Dispatch {

	public static final String componentName = "FP3530.ICSFP3530";

	public ICSFP3530() {
		super(componentName);
	}

	/**
	* This constructor is used instead of a case operation to
	* turn a Dispatch object into a wider object - it must exist
	* in every wrapper class whose instances may be returned from
	* method calls wrapped in VT_DISPATCH Variants.
	*/
	public ICSFP3530(Dispatch d) {
		// take over the IDispatch pointer
		m_pDispatch = d.m_pDispatch;
		// null out the input's pointer
		d.m_pDispatch = 0;
	}

	public ICSFP3530(String compName) {
		super(compName);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lineCount an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean paperFeed(int lineCount) {
		return Dispatch.call(this, "PaperFeed", new Variant(lineCount)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param port an input-parameter of type int
	 * @param boudRate an input-parameter of type int
	 * @param stopBits an input-parameter of type byte
	 * @param parity an input-parameter of type byte
	 * @param byteSize an input-parameter of type byte
	 */
	public void init(int port, int boudRate, byte stopBits, byte parity, byte byteSize) {
		Dispatch.call(this, "Init", new Variant(port), new Variant(boudRate), new Variant(stopBits), new Variant(parity), new Variant(byteSize));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getLastErrorMessage() {
		return Dispatch.call(this, "GetLastErrorMessage").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getLastErrorCode() {
		return Dispatch.call(this, "GetLastErrorCode").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean clearDisplay() {
		return Dispatch.call(this, "ClearDisplay").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type int
	 * @param errorCode an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean openNonFiscalCheck(int allReceipt, byte errorCode) {
		return Dispatch.call(this, "OpenNonFiscalCheck", new Variant(allReceipt), new Variant(errorCode)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean openNonFiscalCheck(int[] allReceipt, byte[] errorCode) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putByteRef(errorCode[0]);

		boolean result_of_OpenNonFiscalCheck = Dispatch.call(this, "OpenNonFiscalCheck", vnt_allReceipt, vnt_errorCode).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toByte();

		return result_of_OpenNonFiscalCheck;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean closeNonFiscalCheck(int allReceipt) {
		return Dispatch.call(this, "CloseNonFiscalCheck", new Variant(allReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean closeNonFiscalCheck(int[] allReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		boolean result_of_CloseNonFiscalCheck = Dispatch.call(this, "CloseNonFiscalCheck", vnt_allReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();

		return result_of_CloseNonFiscalCheck;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean printNonFiscalText(String text) {
		return Dispatch.call(this, "PrintNonFiscalText", text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lineNumber an input-parameter of type int
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean setHeaderAndFooter(int lineNumber, String text) {
		return Dispatch.call(this, "SetHeaderAndFooter", new Variant(lineNumber), text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param emptyLineAfterHeader an input-parameter of type boolean
	 * @param emptyLineAfterTaxNumber an input-parameter of type boolean
	 * @param emptyLineAfterFooter an input-parameter of type boolean
	 * @param lineBeforeTotalSum an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean setHeaderAndFooterOptions(boolean emptyLineAfterHeader, boolean emptyLineAfterTaxNumber, boolean emptyLineAfterFooter, boolean lineBeforeTotalSum) {
		return Dispatch.call(this, "SetHeaderAndFooterOptions", new Variant(emptyLineAfterHeader), new Variant(emptyLineAfterTaxNumber), new Variant(emptyLineAfterFooter), new Variant(lineBeforeTotalSum)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allow an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean allowHeader(boolean allow) {
		return Dispatch.call(this, "AllowHeader", new Variant(allow)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param operatorPassword an input-parameter of type String
	 * @param tillNumber an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean openFiscalCheck(int operatorCode, String operatorPassword, byte tillNumber) {
		return Dispatch.call(this, "OpenFiscalCheck", new Variant(operatorCode), operatorPassword, new Variant(tillNumber)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean closeFiscalCheck() {
		return Dispatch.call(this, "CloseFiscalCheck").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param percent an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sell(String line1, String line2, byte taxCode, double price, double percent) {
            return Dispatch.call(this, "Sell", line1, line2, new Variant(taxCode), new Variant(price), new Variant(percent)).changeType(Variant.VariantBoolean).getBoolean();
//                        return Dispatch.call(this, "Sell", line1, line2, new Variant(taxCode), new Variant(price), new Variant(percent)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param percent an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sellEx(String line1, byte taxCode, double price, double percent) {
		return Dispatch.call(this, "SellEx", line1, new Variant(taxCode), new Variant(price), new Variant(percent)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param payMode an input-parameter of type byte
	 * @param amount an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean total(String line1, String line2, byte payMode, double amount) {
		return Dispatch.call(this, "Total", line1, line2, new Variant(payMode), new Variant(amount)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param onUpperLine an input-parameter of type boolean
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean displayText(boolean onUpperLine, String text) {
		return Dispatch.call(this, "DisplayText", new Variant(onUpperLine), text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean displayDateTime() {
		return Dispatch.call(this, "DisplayDateTime").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean displayTextEx(String text) {
		return Dispatch.call(this, "DisplayTextEx", text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
	 * @param total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean subTotal(boolean toPrint, boolean toDisplay, double perc, double total, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "SubTotal", new Variant(toPrint), new Variant(toDisplay), new Variant(perc), new Variant(total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean subTotal(boolean toPrint, boolean toDisplay, double perc, double[] total, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putDoubleRef(total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_SubTotal = Dispatch.call(this, "SubTotal", new Variant(toPrint), new Variant(toDisplay), new Variant(perc), vnt_total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( total != null && total.length > 0 )
			total[0] = vnt_total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_SubTotal;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean printFiscalText(String text) {
		return Dispatch.call(this, "PrintFiscalText", text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getDateTime(java.util.Date dateTime) {
		return Dispatch.call(this, "GetDateTime", new Variant(dateTime)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getDateTime(java.util.Date[] dateTime) {
		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GetDateTime = Dispatch.call(this, "GetDateTime", vnt_dateTime).changeType(Variant.VariantBoolean).getBoolean();

		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GetDateTime;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean setDateTime(java.util.Date dateTime) {
		return Dispatch.call(this, "SetDateTime", new Variant(dateTime)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param number an input-parameter of type int
	 * @param total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getLastFiscalRecordInfo(int number, double total, double taxA, double taxB, double taxC, double taxD, java.util.Date dateTime) {
		return Dispatch.call(this, "GetLastFiscalRecordInfo", new Variant(number), new Variant(total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(dateTime)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param number is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getLastFiscalRecordInfo(int[] number, double[] total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, java.util.Date[] dateTime) {
		Variant vnt_number = new Variant();
		if( number == null || number.length == 0 )
			vnt_number.putNoParam();
		else
			vnt_number.putIntRef(number[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putDoubleRef(total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GetLastFiscalRecordInfo = Dispatch.call(this, "GetLastFiscalRecordInfo", vnt_number, vnt_total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_dateTime).changeType(Variant.VariantBoolean).getBoolean();

		if( number != null && number.length > 0 )
			number[0] = vnt_number.toInt();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GetLastFiscalRecordInfo;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param zRate an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean getDailyTaxes(double zRate, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "GetDailyTaxes", new Variant(zRate), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param zRate is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getDailyTaxes(double[] zRate, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_zRate = new Variant();
		if( zRate == null || zRate.length == 0 )
			vnt_zRate.putNoParam();
		else
			vnt_zRate.putDoubleRef(zRate[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_GetDailyTaxes = Dispatch.call(this, "GetDailyTaxes", vnt_zRate, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( zRate != null && zRate.length > 0 )
			zRate[0] = vnt_zRate.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_GetDailyTaxes;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure an input-parameter of type int
	 * @param dailyTotal an input-parameter of type double
	 * @param progTotal an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean fiscalMemoryReview(int closure, double dailyTotal, double progTotal) {
		return Dispatch.call(this, "FiscalMemoryReview", new Variant(closure), new Variant(dailyTotal), new Variant(progTotal)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure an input-parameter of type int
	 * @param dailyTotal is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param progTotal is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean fiscalMemoryReview(int closure, double[] dailyTotal, double[] progTotal) {
		Variant vnt_dailyTotal = new Variant();
		if( dailyTotal == null || dailyTotal.length == 0 )
			vnt_dailyTotal.putNoParam();
		else
			vnt_dailyTotal.putDoubleRef(dailyTotal[0]);

		Variant vnt_progTotal = new Variant();
		if( progTotal == null || progTotal.length == 0 )
			vnt_progTotal.putNoParam();
		else
			vnt_progTotal.putDoubleRef(progTotal[0]);

		boolean result_of_FiscalMemoryReview = Dispatch.call(this, "FiscalMemoryReview", new Variant(closure), vnt_dailyTotal, vnt_progTotal).changeType(Variant.VariantBoolean).getBoolean();

		if( dailyTotal != null && dailyTotal.length > 0 )
			dailyTotal[0] = vnt_dailyTotal.toDouble();
		if( progTotal != null && progTotal.length > 0 )
			progTotal[0] = vnt_progTotal.toDouble();

		return result_of_FiscalMemoryReview;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param zRate an input-parameter of type double
	 * @param negTotal an input-parameter of type double
	 * @param notPaied an input-parameter of type double
	 * @param fiscalReceipt an input-parameter of type int
	 * @param allReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getDailySums(double zRate, double negTotal, double notPaied, int fiscalReceipt, int allReceipt) {
		return Dispatch.call(this, "GetDailySums", new Variant(zRate), new Variant(negTotal), new Variant(notPaied), new Variant(fiscalReceipt), new Variant(allReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param zRate is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param negTotal is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param notPaied is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param fiscalReceipt is an one-element array which sends the input-parameter
	 *                      to the ActiveX-Component and receives the output-parameter
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getDailySums(double[] zRate, double[] negTotal, double[] notPaied, int[] fiscalReceipt, int[] allReceipt) {
		Variant vnt_zRate = new Variant();
		if( zRate == null || zRate.length == 0 )
			vnt_zRate.putNoParam();
		else
			vnt_zRate.putDoubleRef(zRate[0]);

		Variant vnt_negTotal = new Variant();
		if( negTotal == null || negTotal.length == 0 )
			vnt_negTotal.putNoParam();
		else
			vnt_negTotal.putDoubleRef(negTotal[0]);

		Variant vnt_notPaied = new Variant();
		if( notPaied == null || notPaied.length == 0 )
			vnt_notPaied.putNoParam();
		else
			vnt_notPaied.putDoubleRef(notPaied[0]);

		Variant vnt_fiscalReceipt = new Variant();
		if( fiscalReceipt == null || fiscalReceipt.length == 0 )
			vnt_fiscalReceipt.putNoParam();
		else
			vnt_fiscalReceipt.putIntRef(fiscalReceipt[0]);

		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		boolean result_of_GetDailySums = Dispatch.call(this, "GetDailySums", vnt_zRate, vnt_negTotal, vnt_notPaied, vnt_fiscalReceipt, vnt_allReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( zRate != null && zRate.length > 0 )
			zRate[0] = vnt_zRate.toDouble();
		if( negTotal != null && negTotal.length > 0 )
			negTotal[0] = vnt_negTotal.toDouble();
		if( notPaied != null && notPaied.length > 0 )
			notPaied[0] = vnt_notPaied.toDouble();
		if( fiscalReceipt != null && fiscalReceipt.length > 0 )
			fiscalReceipt[0] = vnt_fiscalReceipt.toInt();
		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();

		return result_of_GetDailySums;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriod(double taxA, double taxB, double taxC, double taxD, java.util.Date dateTime) {
		return Dispatch.call(this, "GetTaxesByPeriod", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(dateTime)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriod(double[] taxA, double[] taxB, double[] taxC, double[] taxD, java.util.Date[] dateTime) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GetTaxesByPeriod = Dispatch.call(this, "GetTaxesByPeriod", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_dateTime).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GetTaxesByPeriod;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fromDate an input-parameter of type java.util.Date
	 * @param toDate an input-parameter of type java.util.Date
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param lastDate an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriodEx(java.util.Date fromDate, java.util.Date toDate, double taxA, double taxB, double taxC, double taxD, java.util.Date lastDate) {
		return Dispatch.call(this, "GetTaxesByPeriodEx", new Variant(fromDate), new Variant(toDate), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(lastDate)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param fromDate an input-parameter of type java.util.Date
	 * @param toDate an input-parameter of type java.util.Date
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param lastDate is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriodEx(java.util.Date fromDate, java.util.Date toDate, double[] taxA, double[] taxB, double[] taxC, double[] taxD, java.util.Date[] lastDate) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_lastDate = new Variant();
		if( lastDate == null || lastDate.length == 0 )
			vnt_lastDate.putNoParam();
		else
			vnt_lastDate.putDateRef(lastDate[0]);

		boolean result_of_GetTaxesByPeriodEx = Dispatch.call(this, "GetTaxesByPeriodEx", new Variant(fromDate), new Variant(toDate), vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_lastDate).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( lastDate != null && lastDate.length > 0 )
			lastDate[0] = vnt_lastDate.toJavaDate();

		return result_of_GetTaxesByPeriodEx;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param freeRecords an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getFreeFisclalRecords(int freeRecords) {
		return Dispatch.call(this, "GetFreeFisclalRecords", new Variant(freeRecords)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param freeRecords is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getFreeFisclalRecords(int[] freeRecords) {
		Variant vnt_freeRecords = new Variant();
		if( freeRecords == null || freeRecords.length == 0 )
			vnt_freeRecords.putNoParam();
		else
			vnt_freeRecords.putIntRef(freeRecords[0]);

		boolean result_of_GetFreeFisclalRecords = Dispatch.call(this, "GetFreeFisclalRecords", vnt_freeRecords).changeType(Variant.VariantBoolean).getBoolean();

		if( freeRecords != null && freeRecords.length > 0 )
			freeRecords[0] = vnt_freeRecords.toInt();

		return result_of_GetFreeFisclalRecords;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dailyReport(int reportKind, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "DailyReport", new Variant(reportKind), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dailyReport(int reportKind, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_DailyReport = Dispatch.call(this, "DailyReport", new Variant(reportKind), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_DailyReport;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param amount an input-parameter of type double
	 * @param cashSum an input-parameter of type double
	 * @param servIn an input-parameter of type double
	 * @param servOut an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean setServiceMoney(double amount, double cashSum, double servIn, double servOut) {
		return Dispatch.call(this, "SetServiceMoney", new Variant(amount), new Variant(cashSum), new Variant(servIn), new Variant(servOut)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param amount an input-parameter of type double
	 * @param cashSum is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param servIn is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param servOut is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean setServiceMoney(double amount, double[] cashSum, double[] servIn, double[] servOut) {
		Variant vnt_cashSum = new Variant();
		if( cashSum == null || cashSum.length == 0 )
			vnt_cashSum.putNoParam();
		else
			vnt_cashSum.putDoubleRef(cashSum[0]);

		Variant vnt_servIn = new Variant();
		if( servIn == null || servIn.length == 0 )
			vnt_servIn.putNoParam();
		else
			vnt_servIn.putDoubleRef(servIn[0]);

		Variant vnt_servOut = new Variant();
		if( servOut == null || servOut.length == 0 )
			vnt_servOut.putNoParam();
		else
			vnt_servOut.putDoubleRef(servOut[0]);

		boolean result_of_SetServiceMoney = Dispatch.call(this, "SetServiceMoney", new Variant(amount), vnt_cashSum, vnt_servIn, vnt_servOut).changeType(Variant.VariantBoolean).getBoolean();

		if( cashSum != null && cashSum.length > 0 )
			cashSum[0] = vnt_cashSum.toDouble();
		if( servIn != null && servIn.length > 0 )
			servIn[0] = vnt_servIn.toDouble();
		if( servOut != null && servOut.length > 0 )
			servOut[0] = vnt_servOut.toDouble();

		return result_of_SetServiceMoney;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean printDiagnostic() {
		return Dispatch.call(this, "PrintDiagnostic").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param serial an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean fiscalize(String serial) {
		return Dispatch.call(this, "Fiscalize", serial).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startRec an input-parameter of type int
	 * @param endRec an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean printMemoryRecords(int startRec, int endRec) {
		return Dispatch.call(this, "PrintMemoryRecords", new Variant(startRec), new Variant(endRec)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param waitForPrinter an input-parameter of type boolean
	 * @param s0 an input-parameter of type byte
	 * @param s1 an input-parameter of type byte
	 * @param s2 an input-parameter of type byte
	 * @param s3 an input-parameter of type byte
	 * @param s4 an input-parameter of type byte
	 * @param s5 an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean getPrinterStatus(boolean waitForPrinter, byte s0, byte s1, byte s2, byte s3, byte s4, byte s5) {
		return Dispatch.call(this, "GetPrinterStatus", new Variant(waitForPrinter), new Variant(s0), new Variant(s1), new Variant(s2), new Variant(s3), new Variant(s4), new Variant(s5)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param waitForPrinter an input-parameter of type boolean
	 * @param s0 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s1 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s2 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s3 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s4 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s5 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getPrinterStatus(boolean waitForPrinter, byte[] s0, byte[] s1, byte[] s2, byte[] s3, byte[] s4, byte[] s5) {
		Variant vnt_s0 = new Variant();
		if( s0 == null || s0.length == 0 )
			vnt_s0.putNoParam();
		else
			vnt_s0.putByteRef(s0[0]);

		Variant vnt_s1 = new Variant();
		if( s1 == null || s1.length == 0 )
			vnt_s1.putNoParam();
		else
			vnt_s1.putByteRef(s1[0]);

		Variant vnt_s2 = new Variant();
		if( s2 == null || s2.length == 0 )
			vnt_s2.putNoParam();
		else
			vnt_s2.putByteRef(s2[0]);

		Variant vnt_s3 = new Variant();
		if( s3 == null || s3.length == 0 )
			vnt_s3.putNoParam();
		else
			vnt_s3.putByteRef(s3[0]);

		Variant vnt_s4 = new Variant();
		if( s4 == null || s4.length == 0 )
			vnt_s4.putNoParam();
		else
			vnt_s4.putByteRef(s4[0]);

		Variant vnt_s5 = new Variant();
		if( s5 == null || s5.length == 0 )
			vnt_s5.putNoParam();
		else
			vnt_s5.putByteRef(s5[0]);

		boolean result_of_GetPrinterStatus = Dispatch.call(this, "GetPrinterStatus", new Variant(waitForPrinter), vnt_s0, vnt_s1, vnt_s2, vnt_s3, vnt_s4, vnt_s5).changeType(Variant.VariantBoolean).getBoolean();

		if( s0 != null && s0.length > 0 )
			s0[0] = vnt_s0.toByte();
		if( s1 != null && s1.length > 0 )
			s1[0] = vnt_s1.toByte();
		if( s2 != null && s2.length > 0 )
			s2[0] = vnt_s2.toByte();
		if( s3 != null && s3.length > 0 )
			s3[0] = vnt_s3.toByte();
		if( s4 != null && s4.length > 0 )
			s4[0] = vnt_s4.toByte();
		if( s5 != null && s5.length > 0 )
			s5[0] = vnt_s5.toByte();

		return result_of_GetPrinterStatus;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param askForCurrentAmount an input-parameter of type boolean
	 * @param open an input-parameter of type int
	 * @param items an input-parameter of type int
	 * @param amount an input-parameter of type double
	 * @param tender an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean checkFiscalCloseStatus(boolean askForCurrentAmount, int open, int items, double amount, double tender) {
		return Dispatch.call(this, "CheckFiscalCloseStatus", new Variant(askForCurrentAmount), new Variant(open), new Variant(items), new Variant(amount), new Variant(tender)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param askForCurrentAmount an input-parameter of type boolean
	 * @param open is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param items is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param tender is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean checkFiscalCloseStatus(boolean askForCurrentAmount, int[] open, int[] items, double[] amount, double[] tender) {
		Variant vnt_open = new Variant();
		if( open == null || open.length == 0 )
			vnt_open.putNoParam();
		else
			vnt_open.putIntRef(open[0]);

		Variant vnt_items = new Variant();
		if( items == null || items.length == 0 )
			vnt_items.putNoParam();
		else
			vnt_items.putIntRef(items[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putDoubleRef(amount[0]);

		Variant vnt_tender = new Variant();
		if( tender == null || tender.length == 0 )
			vnt_tender.putNoParam();
		else
			vnt_tender.putDoubleRef(tender[0]);

		boolean result_of_CheckFiscalCloseStatus = Dispatch.call(this, "CheckFiscalCloseStatus", new Variant(askForCurrentAmount), vnt_open, vnt_items, vnt_amount, vnt_tender).changeType(Variant.VariantBoolean).getBoolean();

		if( open != null && open.length > 0 )
			open[0] = vnt_open.toInt();
		if( items != null && items.length > 0 )
			items[0] = vnt_items.toInt();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toDouble();
		if( tender != null && tender.length > 0 )
			tender[0] = vnt_tender.toDouble();

		return result_of_CheckFiscalCloseStatus;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startDate an input-parameter of type java.util.Date
	 * @param endDate an input-parameter of type java.util.Date
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean getSumsByPeriod(java.util.Date startDate, java.util.Date endDate, double fM_Total, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "GetSumsByPeriod", new Variant(startDate), new Variant(endDate), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param startDate an input-parameter of type java.util.Date
	 * @param endDate an input-parameter of type java.util.Date
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getSumsByPeriod(java.util.Date startDate, java.util.Date endDate, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_GetSumsByPeriod = Dispatch.call(this, "GetSumsByPeriod", new Variant(startDate), new Variant(endDate), vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_GetSumsByPeriod;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param calcCRC an input-parameter of type boolean
	 * @param fvrew an input-parameter of type String
	 * @param cRC an input-parameter of type String
	 * @param country an input-parameter of type int
	 * @param dateTime an input-parameter of type String
	 * @param serialNumber an input-parameter of type String
	 * @param fiscalMemoryNumber an input-parameter of type String
	 * @param switches an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean getDiagnosticInfo(boolean calcCRC, String fvrew, String cRC, int country, String dateTime, String serialNumber, String fiscalMemoryNumber, String switches) {
		return Dispatch.call(this, "GetDiagnosticInfo", new Variant(calcCRC), fvrew, cRC, new Variant(country), dateTime, serialNumber, fiscalMemoryNumber, switches).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param calcCRC an input-parameter of type boolean
	 * @param fvrew is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param cRC is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param country is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param serialNumber is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param fiscalMemoryNumber is an one-element array which sends the input-parameter
	 *                           to the ActiveX-Component and receives the output-parameter
	 * @param switches is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getDiagnosticInfo(boolean calcCRC, String[] fvrew, String[] cRC, int[] country, String[] dateTime, String[] serialNumber, String[] fiscalMemoryNumber, String[] switches) {
		Variant vnt_fvrew = new Variant();
		if( fvrew == null || fvrew.length == 0 )
			vnt_fvrew.putNoParam();
		else
			vnt_fvrew.putStringRef(fvrew[0]);

		Variant vnt_cRC = new Variant();
		if( cRC == null || cRC.length == 0 )
			vnt_cRC.putNoParam();
		else
			vnt_cRC.putStringRef(cRC[0]);

		Variant vnt_country = new Variant();
		if( country == null || country.length == 0 )
			vnt_country.putNoParam();
		else
			vnt_country.putIntRef(country[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putStringRef(dateTime[0]);

		Variant vnt_serialNumber = new Variant();
		if( serialNumber == null || serialNumber.length == 0 )
			vnt_serialNumber.putNoParam();
		else
			vnt_serialNumber.putStringRef(serialNumber[0]);

		Variant vnt_fiscalMemoryNumber = new Variant();
		if( fiscalMemoryNumber == null || fiscalMemoryNumber.length == 0 )
			vnt_fiscalMemoryNumber.putNoParam();
		else
			vnt_fiscalMemoryNumber.putStringRef(fiscalMemoryNumber[0]);

		Variant vnt_switches = new Variant();
		if( switches == null || switches.length == 0 )
			vnt_switches.putNoParam();
		else
			vnt_switches.putStringRef(switches[0]);

		boolean result_of_GetDiagnosticInfo = Dispatch.call(this, "GetDiagnosticInfo", new Variant(calcCRC), vnt_fvrew, vnt_cRC, vnt_country, vnt_dateTime, vnt_serialNumber, vnt_fiscalMemoryNumber, vnt_switches).changeType(Variant.VariantBoolean).getBoolean();

		if( fvrew != null && fvrew.length > 0 )
			fvrew[0] = vnt_fvrew.toString();
		if( cRC != null && cRC.length > 0 )
			cRC[0] = vnt_cRC.toString();
		if( country != null && country.length > 0 )
			country[0] = vnt_country.toInt();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toString();
		if( serialNumber != null && serialNumber.length > 0 )
			serialNumber[0] = vnt_serialNumber.toString();
		if( fiscalMemoryNumber != null && fiscalMemoryNumber.length > 0 )
			fiscalMemoryNumber[0] = vnt_fiscalMemoryNumber.toString();
		if( switches != null && switches.length > 0 )
			switches[0] = vnt_switches.toString();

		return result_of_GetDiagnosticInfo;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fromDate an input-parameter of type String
	 * @param toDate an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean printFiscalMemoryByDates(String fromDate, String toDate) {
		return Dispatch.call(this, "PrintFiscalMemoryByDates", fromDate, toDate).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type int
	 * @param taxB an input-parameter of type int
	 * @param taxC an input-parameter of type int
	 * @param taxD an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean setTaxes(int taxA, int taxB, int taxC, int taxD) {
		return Dispatch.call(this, "SetTaxes", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type int
	 * @param taxB an input-parameter of type int
	 * @param taxC an input-parameter of type int
	 * @param taxD an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getTaxes(int taxA, int taxB, int taxC, int taxD) {
		return Dispatch.call(this, "GetTaxes", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxes(int[] taxA, int[] taxB, int[] taxC, int[] taxD) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putIntRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putIntRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putIntRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putIntRef(taxD[0]);

		boolean result_of_GetTaxes = Dispatch.call(this, "GetTaxes", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toInt();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toInt();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toInt();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toInt();

		return result_of_GetTaxes;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxNumber an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean setTaxNumber(String taxNumber) {
		return Dispatch.call(this, "SetTaxNumber", taxNumber).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxNumber an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean getTaxNumber(String taxNumber) {
		return Dispatch.call(this, "GetTaxNumber", taxNumber).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxNumber is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxNumber(String[] taxNumber) {
		Variant vnt_taxNumber = new Variant();
		if( taxNumber == null || taxNumber.length == 0 )
			vnt_taxNumber.putNoParam();
		else
			vnt_taxNumber.putStringRef(taxNumber[0]);

		boolean result_of_GetTaxNumber = Dispatch.call(this, "GetTaxNumber", vnt_taxNumber).changeType(Variant.VariantBoolean).getBoolean();

		if( taxNumber != null && taxNumber.length > 0 )
			taxNumber[0] = vnt_taxNumber.toString();

		return result_of_GetTaxNumber;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param oldPassword an input-parameter of type String
	 * @param newPassword an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean setOperatorPassword(int opCode, String oldPassword, String newPassword) {
		return Dispatch.call(this, "SetOperatorPassword", new Variant(opCode), oldPassword, newPassword).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param password an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean setOperatorName(int opCode, String password, String name) {
		return Dispatch.call(this, "SetOperatorName", new Variant(opCode), password, name).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param canVoid an input-parameter of type boolean
	 * @param inv an input-parameter of type boolean
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param invNumber an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getCurrentCheckInfo(boolean canVoid, boolean inv, double taxA, double taxB, double taxC, double taxD, int invNumber) {
		return Dispatch.call(this, "GetCurrentCheckInfo", new Variant(canVoid), new Variant(inv), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(invNumber)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param canVoid is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param inv is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param invNumber is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getCurrentCheckInfo(boolean[] canVoid, boolean[] inv, double[] taxA, double[] taxB, double[] taxC, double[] taxD, int[] invNumber) {
		Variant vnt_canVoid = new Variant();
		if( canVoid == null || canVoid.length == 0 )
			vnt_canVoid.putNoParam();
		else
			vnt_canVoid.putBooleanRef(canVoid[0]);

		Variant vnt_inv = new Variant();
		if( inv == null || inv.length == 0 )
			vnt_inv.putNoParam();
		else
			vnt_inv.putBooleanRef(inv[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_invNumber = new Variant();
		if( invNumber == null || invNumber.length == 0 )
			vnt_invNumber.putNoParam();
		else
			vnt_invNumber.putIntRef(invNumber[0]);

		boolean result_of_GetCurrentCheckInfo = Dispatch.call(this, "GetCurrentCheckInfo", vnt_canVoid, vnt_inv, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_invNumber).changeType(Variant.VariantBoolean).getBoolean();

		if( canVoid != null && canVoid.length > 0 )
			canVoid[0] = vnt_canVoid.toBoolean();
		if( inv != null && inv.length > 0 )
			inv[0] = vnt_inv.toBoolean();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( invNumber != null && invNumber.length > 0 )
			invNumber[0] = vnt_invNumber.toInt();

		return result_of_GetCurrentCheckInfo;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param count an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean printDoubleCheck(int count) {
		return Dispatch.call(this, "PrintDoubleCheck", new Variant(count)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cash an input-parameter of type double
	 * @param credit an input-parameter of type double
	 * @param debit an input-parameter of type double
	 * @param cheque an input-parameter of type double
	 * @param closure an input-parameter of type int
	 * @param receipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getExtraDailyInfo(double cash, double credit, double debit, double cheque, int closure, int receipt) {
		return Dispatch.call(this, "GetExtraDailyInfo", new Variant(cash), new Variant(credit), new Variant(debit), new Variant(cheque), new Variant(closure), new Variant(receipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cash is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param credit is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param debit is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param cheque is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipt is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getExtraDailyInfo(double[] cash, double[] credit, double[] debit, double[] cheque, int[] closure, int[] receipt) {
		Variant vnt_cash = new Variant();
		if( cash == null || cash.length == 0 )
			vnt_cash.putNoParam();
		else
			vnt_cash.putDoubleRef(cash[0]);

		Variant vnt_credit = new Variant();
		if( credit == null || credit.length == 0 )
			vnt_credit.putNoParam();
		else
			vnt_credit.putDoubleRef(credit[0]);

		Variant vnt_debit = new Variant();
		if( debit == null || debit.length == 0 )
			vnt_debit.putNoParam();
		else
			vnt_debit.putDoubleRef(debit[0]);

		Variant vnt_cheque = new Variant();
		if( cheque == null || cheque.length == 0 )
			vnt_cheque.putNoParam();
		else
			vnt_cheque.putDoubleRef(cheque[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_receipt = new Variant();
		if( receipt == null || receipt.length == 0 )
			vnt_receipt.putNoParam();
		else
			vnt_receipt.putIntRef(receipt[0]);

		boolean result_of_GetExtraDailyInfo = Dispatch.call(this, "GetExtraDailyInfo", vnt_cash, vnt_credit, vnt_debit, vnt_cheque, vnt_closure, vnt_receipt).changeType(Variant.VariantBoolean).getBoolean();

		if( cash != null && cash.length > 0 )
			cash[0] = vnt_cash.toDouble();
		if( credit != null && credit.length > 0 )
			credit[0] = vnt_credit.toDouble();
		if( debit != null && debit.length > 0 )
			debit[0] = vnt_debit.toDouble();
		if( cheque != null && cheque.length > 0 )
			cheque[0] = vnt_cheque.toDouble();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( receipt != null && receipt.length > 0 )
			receipt[0] = vnt_receipt.toInt();

		return result_of_GetExtraDailyInfo;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param docNum an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean getLastPrintDoc(int docNum) {
		return Dispatch.call(this, "GetLastPrintDoc", new Variant(docNum)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param docNum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getLastPrintDoc(int[] docNum) {
		Variant vnt_docNum = new Variant();
		if( docNum == null || docNum.length == 0 )
			vnt_docNum.putNoParam();
		else
			vnt_docNum.putIntRef(docNum[0]);

		boolean result_of_GetLastPrintDoc = Dispatch.call(this, "GetLastPrintDoc", vnt_docNum).changeType(Variant.VariantBoolean).getBoolean();

		if( docNum != null && docNum.length > 0 )
			docNum[0] = vnt_docNum.toInt();

		return result_of_GetLastPrintDoc;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param mSec an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean openTill(int mSec) {
		return Dispatch.call(this, "OpenTill", new Variant(mSec)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param mSec is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean openTill(int[] mSec) {
		Variant vnt_mSec = new Variant();
		if( mSec == null || mSec.length == 0 )
			vnt_mSec.putNoParam();
		else
			vnt_mSec.putIntRef(mSec[0]);

		boolean result_of_OpenTill = Dispatch.call(this, "OpenTill", vnt_mSec).changeType(Variant.VariantBoolean).getBoolean();

		if( mSec != null && mSec.length > 0 )
			mSec[0] = vnt_mSec.toInt();

		return result_of_OpenTill;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dayliReportExp(int reportKind, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "DayliReportExp", new Variant(reportKind), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dayliReportExp(int reportKind, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_DayliReportExp = Dispatch.call(this, "DayliReportExp", new Variant(reportKind), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_DayliReportExp;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fromDate an input-parameter of type String
	 * @param toDate an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean printFiscMemByDatesShort(String fromDate, String toDate) {
		return Dispatch.call(this, "PrintFiscMemByDatesShort", fromDate, toDate).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startRec an input-parameter of type int
	 * @param endRec an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean shortRepMemByRec(int startRec, int endRec) {
		return Dispatch.call(this, "ShortRepMemByRec", new Variant(startRec), new Variant(endRec)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allow1 an input-parameter of type boolean
	 * @param allow2 an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean allowLogo(boolean allow1, boolean allow2) {
		return Dispatch.call(this, "AllowLogo", new Variant(allow1), new Variant(allow2)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allow an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean allowInvoice_3530(boolean allow) {
		return Dispatch.call(this, "AllowInvoice_3530", new Variant(allow)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param command an input-parameter of type byte
	 * @param rowNum an input-parameter of type byte
	 * @param string45Bytes an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean setLogoRow_3530(byte command, byte rowNum, String string45Bytes) {
		return Dispatch.call(this, "SetLogoRow_3530", new Variant(command), new Variant(rowNum), string45Bytes).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String checkFCloseStat() {
		return Dispatch.call(this, "CheckFCloseStat").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param percent an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sellQ(String line1, String line2, byte taxCode, double price, double quantity, double percent) {
		return Dispatch.call(this, "SellQ", line1, line2, new Variant(taxCode), new Variant(price), new Variant(quantity), new Variant(percent)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxNumber an input-parameter of type String
	 * @param seller an input-parameter of type String
	 * @param receiver an input-parameter of type String
	 * @param client an input-parameter of type String
	 * @param bulstat an input-parameter of type String
	 * @param address1 an input-parameter of type String
	 * @param address2 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean printClientInfo(String taxNumber, String seller, String receiver, String client, String bulstat, String address1, String address2) {
		return Dispatch.call(this, "PrintClientInfo", taxNumber, seller, receiver, client, bulstat, address1, address2).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param operatorPassword an input-parameter of type String
	 * @param tillNumber an input-parameter of type byte
	 * @param invoice an input-parameter of type boolean
	 * @param allReceipt an input-parameter of type int
	 * @param fiscReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean openInvoiceCheck(int operatorCode, String operatorPassword, byte tillNumber, boolean invoice, int allReceipt, int fiscReceipt) {
		return Dispatch.call(this, "OpenInvoiceCheck", new Variant(operatorCode), operatorPassword, new Variant(tillNumber), new Variant(invoice), new Variant(allReceipt), new Variant(fiscReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param operatorPassword an input-parameter of type String
	 * @param tillNumber an input-parameter of type byte
	 * @param invoice an input-parameter of type boolean
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean openInvoiceCheck(int operatorCode, String operatorPassword, byte tillNumber, boolean invoice, int[] allReceipt, int[] fiscReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		Variant vnt_fiscReceipt = new Variant();
		if( fiscReceipt == null || fiscReceipt.length == 0 )
			vnt_fiscReceipt.putNoParam();
		else
			vnt_fiscReceipt.putIntRef(fiscReceipt[0]);

		boolean result_of_OpenInvoiceCheck = Dispatch.call(this, "OpenInvoiceCheck", new Variant(operatorCode), operatorPassword, new Variant(tillNumber), new Variant(invoice), vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toInt();

		return result_of_OpenInvoiceCheck;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operator an input-parameter of type byte
	 * @param receipts an input-parameter of type int
	 * @param salesNumber an input-parameter of type int
	 * @param salesSum an input-parameter of type long
	 * @param discountNumber an input-parameter of type int
	 * @param discountSum an input-parameter of type long
	 * @param surchargeNumber an input-parameter of type int
	 * @param surchargeSum an input-parameter of type long
	 * @param voidNumber an input-parameter of type int
	 * @param voidSum an input-parameter of type long
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean getOperatorInfo(byte operator, int receipts, int salesNumber, long salesSum, int discountNumber, long discountSum, int surchargeNumber, long surchargeSum, int voidNumber, long voidSum, String name) {
		return Dispatch.callN(this, "GetOperatorInfo", new Object[] { operator, new Variant(receipts), new Variant(salesNumber), new Variant(salesSum), new Variant(discountNumber), new Variant(discountSum), new Variant(surchargeNumber), new Variant(surchargeSum), new Variant(voidNumber), new Variant(voidSum), name}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operator an input-parameter of type byte
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param salesNumber is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param salesSum is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param discountNumber is an one-element array which sends the input-parameter
	 *                       to the ActiveX-Component and receives the output-parameter
	 * @param discountSum is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param surchargeNumber is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param surchargeSum is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param voidNumber is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param voidSum is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getOperatorInfo(byte operator, int[] receipts, int[] salesNumber, long[] salesSum, int[] discountNumber, long[] discountSum, int[] surchargeNumber, long[] surchargeSum, int[] voidNumber, long[] voidSum, String[] name) {
		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putIntRef(receipts[0]);

		Variant vnt_salesNumber = new Variant();
		if( salesNumber == null || salesNumber.length == 0 )
			vnt_salesNumber.putNoParam();
		else
			vnt_salesNumber.putIntRef(salesNumber[0]);

		Variant vnt_salesSum = new Variant();
		if( salesSum == null || salesSum.length == 0 )
			vnt_salesSum.putNoParam();
		else
			vnt_salesSum.putLongRef(salesSum[0]);

		Variant vnt_discountNumber = new Variant();
		if( discountNumber == null || discountNumber.length == 0 )
			vnt_discountNumber.putNoParam();
		else
			vnt_discountNumber.putIntRef(discountNumber[0]);

		Variant vnt_discountSum = new Variant();
		if( discountSum == null || discountSum.length == 0 )
			vnt_discountSum.putNoParam();
		else
			vnt_discountSum.putLongRef(discountSum[0]);

		Variant vnt_surchargeNumber = new Variant();
		if( surchargeNumber == null || surchargeNumber.length == 0 )
			vnt_surchargeNumber.putNoParam();
		else
			vnt_surchargeNumber.putIntRef(surchargeNumber[0]);

		Variant vnt_surchargeSum = new Variant();
		if( surchargeSum == null || surchargeSum.length == 0 )
			vnt_surchargeSum.putNoParam();
		else
			vnt_surchargeSum.putLongRef(surchargeSum[0]);

		Variant vnt_voidNumber = new Variant();
		if( voidNumber == null || voidNumber.length == 0 )
			vnt_voidNumber.putNoParam();
		else
			vnt_voidNumber.putIntRef(voidNumber[0]);

		Variant vnt_voidSum = new Variant();
		if( voidSum == null || voidSum.length == 0 )
			vnt_voidSum.putNoParam();
		else
			vnt_voidSum.putLongRef(voidSum[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		boolean result_of_GetOperatorInfo = Dispatch.callN(this, "GetOperatorInfo", new Object[] { operator, vnt_receipts, vnt_salesNumber, vnt_salesSum, vnt_discountNumber, vnt_discountSum, vnt_surchargeNumber, vnt_surchargeSum, vnt_voidNumber, vnt_voidSum, vnt_name}).changeType(Variant.VariantBoolean).getBoolean();

		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toInt();
		if( salesNumber != null && salesNumber.length > 0 )
			salesNumber[0] = vnt_salesNumber.toInt();
		if( salesSum != null && salesSum.length > 0 )
			salesSum[0] = vnt_salesSum.changeType(Variant.VariantLongInt).getLong();
		if( discountNumber != null && discountNumber.length > 0 )
			discountNumber[0] = vnt_discountNumber.toInt();
		if( discountSum != null && discountSum.length > 0 )
			discountSum[0] = vnt_discountSum.changeType(Variant.VariantLongInt).getLong();
		if( surchargeNumber != null && surchargeNumber.length > 0 )
			surchargeNumber[0] = vnt_surchargeNumber.toInt();
		if( surchargeSum != null && surchargeSum.length > 0 )
			surchargeSum[0] = vnt_surchargeSum.changeType(Variant.VariantLongInt).getLong();
		if( voidNumber != null && voidNumber.length > 0 )
			voidNumber[0] = vnt_voidNumber.toInt();
		if( voidSum != null && voidSum.length > 0 )
			voidSum[0] = vnt_voidSum.changeType(Variant.VariantLongInt).getLong();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_GetOperatorInfo;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean truncatePaper() {
		return Dispatch.call(this, "TruncatePaper").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getLastFiscalRecordInfoEx() {
		return Dispatch.call(this, "GetLastFiscalRecordInfoEx").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean itemsReport_Daily() {
		return Dispatch.call(this, "ItemsReport_Daily").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean itemsReport() {
		return Dispatch.call(this, "ItemsReport").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toDisplay an input-parameter of type boolean
	 * @param subTotal an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param taxI an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean subTotalSr(boolean toDisplay, double subTotal, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, double taxI) {
		return Dispatch.callN(this, "SubTotalSr", new Object[] { new Variant(toDisplay), new Variant(subTotal), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(taxI)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toDisplay an input-parameter of type boolean
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
	 * @param taxI is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean subTotalSr(boolean toDisplay, double[] subTotal, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, double[] taxI) {
		Variant vnt_subTotal = new Variant();
		if( subTotal == null || subTotal.length == 0 )
			vnt_subTotal.putNoParam();
		else
			vnt_subTotal.putDoubleRef(subTotal[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_taxI = new Variant();
		if( taxI == null || taxI.length == 0 )
			vnt_taxI.putNoParam();
		else
			vnt_taxI.putDoubleRef(taxI[0]);

		boolean result_of_SubTotalSr = Dispatch.callN(this, "SubTotalSr", new Object[] { new Variant(toDisplay), vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_taxI}).changeType(Variant.VariantBoolean).getBoolean();

		if( subTotal != null && subTotal.length > 0 )
			subTotal[0] = vnt_subTotal.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( taxI != null && taxI.length > 0 )
			taxI[0] = vnt_taxI.toDouble();

		return result_of_SubTotalSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type int
	 * @param taxGroup an input-parameter of type String
	 * @param singlePrice an input-parameter of type String
	 * @param amount an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean readItemSr(int pLU, String taxGroup, String singlePrice, String amount, String name) {
		return Dispatch.call(this, "ReadItemSr", new Variant(pLU), taxGroup, singlePrice, amount, name).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type int
	 * @param taxGroup is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param singlePrice is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean readItemSr(int pLU, String[] taxGroup, String[] singlePrice, String[] amount, String[] name) {
		Variant vnt_taxGroup = new Variant();
		if( taxGroup == null || taxGroup.length == 0 )
			vnt_taxGroup.putNoParam();
		else
			vnt_taxGroup.putStringRef(taxGroup[0]);

		Variant vnt_singlePrice = new Variant();
		if( singlePrice == null || singlePrice.length == 0 )
			vnt_singlePrice.putNoParam();
		else
			vnt_singlePrice.putStringRef(singlePrice[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putStringRef(amount[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		boolean result_of_ReadItemSr = Dispatch.call(this, "ReadItemSr", new Variant(pLU), vnt_taxGroup, vnt_singlePrice, vnt_amount, vnt_name).changeType(Variant.VariantBoolean).getBoolean();

		if( taxGroup != null && taxGroup.length > 0 )
			taxGroup[0] = vnt_taxGroup.toString();
		if( singlePrice != null && singlePrice.length > 0 )
			singlePrice[0] = vnt_singlePrice.toString();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toString();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_ReadItemSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param taxGroup an input-parameter of type String
	 * @param singlePrice an input-parameter of type String
	 * @param amount an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean readFirstItemSr(String pLU, String taxGroup, String singlePrice, String amount, String name) {
		return Dispatch.call(this, "ReadFirstItemSr", pLU, taxGroup, singlePrice, amount, name).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param taxGroup is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param singlePrice is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean readFirstItemSr(String[] pLU, String[] taxGroup, String[] singlePrice, String[] amount, String[] name) {
		Variant vnt_pLU = new Variant();
		if( pLU == null || pLU.length == 0 )
			vnt_pLU.putNoParam();
		else
			vnt_pLU.putStringRef(pLU[0]);

		Variant vnt_taxGroup = new Variant();
		if( taxGroup == null || taxGroup.length == 0 )
			vnt_taxGroup.putNoParam();
		else
			vnt_taxGroup.putStringRef(taxGroup[0]);

		Variant vnt_singlePrice = new Variant();
		if( singlePrice == null || singlePrice.length == 0 )
			vnt_singlePrice.putNoParam();
		else
			vnt_singlePrice.putStringRef(singlePrice[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putStringRef(amount[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		boolean result_of_ReadFirstItemSr = Dispatch.call(this, "ReadFirstItemSr", vnt_pLU, vnt_taxGroup, vnt_singlePrice, vnt_amount, vnt_name).changeType(Variant.VariantBoolean).getBoolean();

		if( pLU != null && pLU.length > 0 )
			pLU[0] = vnt_pLU.toString();
		if( taxGroup != null && taxGroup.length > 0 )
			taxGroup[0] = vnt_taxGroup.toString();
		if( singlePrice != null && singlePrice.length > 0 )
			singlePrice[0] = vnt_singlePrice.toString();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toString();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_ReadFirstItemSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param taxGroup an input-parameter of type String
	 * @param singlePrice an input-parameter of type String
	 * @param amount an input-parameter of type String
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean readNextItemSr(String pLU, String taxGroup, String singlePrice, String amount, String name) {
		return Dispatch.call(this, "ReadNextItemSr", pLU, taxGroup, singlePrice, amount, name).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param taxGroup is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param singlePrice is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param name is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean readNextItemSr(String[] pLU, String[] taxGroup, String[] singlePrice, String[] amount, String[] name) {
		Variant vnt_pLU = new Variant();
		if( pLU == null || pLU.length == 0 )
			vnt_pLU.putNoParam();
		else
			vnt_pLU.putStringRef(pLU[0]);

		Variant vnt_taxGroup = new Variant();
		if( taxGroup == null || taxGroup.length == 0 )
			vnt_taxGroup.putNoParam();
		else
			vnt_taxGroup.putStringRef(taxGroup[0]);

		Variant vnt_singlePrice = new Variant();
		if( singlePrice == null || singlePrice.length == 0 )
			vnt_singlePrice.putNoParam();
		else
			vnt_singlePrice.putStringRef(singlePrice[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putStringRef(amount[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		boolean result_of_ReadNextItemSr = Dispatch.call(this, "ReadNextItemSr", vnt_pLU, vnt_taxGroup, vnt_singlePrice, vnt_amount, vnt_name).changeType(Variant.VariantBoolean).getBoolean();

		if( pLU != null && pLU.length > 0 )
			pLU[0] = vnt_pLU.toString();
		if( taxGroup != null && taxGroup.length > 0 )
			taxGroup[0] = vnt_taxGroup.toString();
		if( singlePrice != null && singlePrice.length > 0 )
			singlePrice[0] = vnt_singlePrice.toString();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toString();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_ReadNextItemSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type int
	 * @param newPrice an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean newItemPriceSr(int pLU, double newPrice) {
		return Dispatch.call(this, "NewItemPriceSr", new Variant(pLU), new Variant(newPrice)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean deleteItemSr(int pLU) {
		return Dispatch.call(this, "DeleteItemSr", new Variant(pLU)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type int
	 * @param taxGroupNumber an input-parameter of type byte
	 * @param singlePrice an input-parameter of type double
	 * @param itemName an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean writeItemSr(int pLU, byte taxGroupNumber, double singlePrice, String itemName) {
		return Dispatch.call(this, "WriteItemSr", new Variant(pLU), new Variant(taxGroupNumber), new Variant(singlePrice), itemName).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clrOpData an input-parameter of type boolean
	 * @param clrQuantData an input-parameter of type boolean
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param taxI an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dailyReportSr(int reportKind, boolean clrOpData, boolean clrQuantData, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, double taxI) {
		return Dispatch.callN(this, "DailyReportSr", new Object[] { new Variant(reportKind), new Variant(clrOpData), new Variant(clrQuantData), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(taxI)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clrOpData an input-parameter of type boolean
	 * @param clrQuantData an input-parameter of type boolean
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
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
	 * @param taxI is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dailyReportSr(int reportKind, boolean clrOpData, boolean clrQuantData, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, double[] taxI) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_taxI = new Variant();
		if( taxI == null || taxI.length == 0 )
			vnt_taxI.putNoParam();
		else
			vnt_taxI.putDoubleRef(taxI[0]);

		boolean result_of_DailyReportSr = Dispatch.callN(this, "DailyReportSr", new Object[] { new Variant(reportKind), new Variant(clrOpData), new Variant(clrQuantData), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_taxI}).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( taxI != null && taxI.length > 0 )
			taxI[0] = vnt_taxI.toDouble();

		return result_of_DailyReportSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param void_ an input-parameter of type boolean
	 * @param pLU an input-parameter of type int
	 * @param quantity an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sellSr(boolean void_, int pLU, double quantity) {
		return Dispatch.call(this, "SellSr", new Variant(void_), new Variant(pLU), new Variant(quantity)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param operatorPassword an input-parameter of type String
	 * @param tillNumber an input-parameter of type byte
	 * @param allReceipt an input-parameter of type int
	 * @param fiscReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean openFiscalCheckEx(int operatorCode, String operatorPassword, byte tillNumber, int allReceipt, int fiscReceipt) {
		return Dispatch.call(this, "OpenFiscalCheckEx", new Variant(operatorCode), operatorPassword, new Variant(tillNumber), new Variant(allReceipt), new Variant(fiscReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operatorCode an input-parameter of type int
	 * @param operatorPassword an input-parameter of type String
	 * @param tillNumber an input-parameter of type byte
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean openFiscalCheckEx(int operatorCode, String operatorPassword, byte tillNumber, int[] allReceipt, int[] fiscReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		Variant vnt_fiscReceipt = new Variant();
		if( fiscReceipt == null || fiscReceipt.length == 0 )
			vnt_fiscReceipt.putNoParam();
		else
			vnt_fiscReceipt.putIntRef(fiscReceipt[0]);

		boolean result_of_OpenFiscalCheckEx = Dispatch.call(this, "OpenFiscalCheckEx", new Variant(operatorCode), operatorPassword, new Variant(tillNumber), vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toInt();

		return result_of_OpenFiscalCheckEx;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param canVoid an input-parameter of type boolean
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param taxI an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean getCurrentCheckInfoSr(boolean canVoid, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, double taxI) {
		return Dispatch.callN(this, "GetCurrentCheckInfoSr", new Object[] { new Variant(canVoid), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(taxI)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param canVoid is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
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
	 * @param taxI is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getCurrentCheckInfoSr(boolean[] canVoid, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, double[] taxI) {
		Variant vnt_canVoid = new Variant();
		if( canVoid == null || canVoid.length == 0 )
			vnt_canVoid.putNoParam();
		else
			vnt_canVoid.putBooleanRef(canVoid[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_taxI = new Variant();
		if( taxI == null || taxI.length == 0 )
			vnt_taxI.putNoParam();
		else
			vnt_taxI.putDoubleRef(taxI[0]);

		boolean result_of_GetCurrentCheckInfoSr = Dispatch.callN(this, "GetCurrentCheckInfoSr", new Object[] { vnt_canVoid, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_taxI}).changeType(Variant.VariantBoolean).getBoolean();

		if( canVoid != null && canVoid.length > 0 )
			canVoid[0] = vnt_canVoid.toBoolean();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( taxI != null && taxI.length > 0 )
			taxI[0] = vnt_taxI.toDouble();

		return result_of_GetCurrentCheckInfoSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param taxI an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean getDailyTaxesSr(double total, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, double taxI) {
		return Dispatch.callN(this, "GetDailyTaxesSr", new Object[] { new Variant(total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(taxI)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
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
	 * @param taxI is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getDailyTaxesSr(double[] total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, double[] taxI) {
		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putDoubleRef(total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_taxI = new Variant();
		if( taxI == null || taxI.length == 0 )
			vnt_taxI.putNoParam();
		else
			vnt_taxI.putDoubleRef(taxI[0]);

		boolean result_of_GetDailyTaxesSr = Dispatch.callN(this, "GetDailyTaxesSr", new Object[] { vnt_total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_taxI}).changeType(Variant.VariantBoolean).getBoolean();

		if( total != null && total.length > 0 )
			total[0] = vnt_total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( taxI != null && taxI.length > 0 )
			taxI[0] = vnt_taxI.toDouble();

		return result_of_GetDailyTaxesSr;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean printOperatorInfo() {
		return Dispatch.call(this, "PrintOperatorInfo").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorCode an input-parameter of type byte
	 * @param password an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean clearOperatorInfo(byte operatorCode, String password) {
		return Dispatch.call(this, "ClearOperatorInfo", operatorCode, password).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT(int reportKind, boolean clearOpInfo, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "DAILY_REPORT", new Variant(reportKind), new Variant(clearOpInfo), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT(int reportKind, boolean clearOpInfo, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_DAILY_REPORT = Dispatch.call(this, "DAILY_REPORT", new Variant(reportKind), new Variant(clearOpInfo), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_DAILY_REPORT;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startDate an input-parameter of type java.util.Date
	 * @param endDate an input-parameter of type java.util.Date
	 * @param detailed an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean pRINT_FMBYDATES(java.util.Date startDate, java.util.Date endDate, boolean detailed) {
		return Dispatch.call(this, "PRINT_FMBYDATES", new Variant(startDate), new Variant(endDate), new Variant(detailed)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param start_DAY an input-parameter of type String
	 * @param start_MONTH an input-parameter of type String
	 * @param start_YEAR an input-parameter of type String
	 * @param end_DAY an input-parameter of type String
	 * @param end_MONTH an input-parameter of type String
	 * @param end_YEAR an input-parameter of type String
	 * @param detailed an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean pRINT_FMBYDATES2(String start_DAY, String start_MONTH, String start_YEAR, String end_DAY, String end_MONTH, String end_YEAR, boolean detailed) {
		return Dispatch.call(this, "PRINT_FMBYDATES2", start_DAY, start_MONTH, start_YEAR, end_DAY, end_MONTH, end_YEAR, new Variant(detailed)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param waitForPrint an input-parameter of type boolean
	 * @param s0 an input-parameter of type int
	 * @param s1 an input-parameter of type int
	 * @param s2 an input-parameter of type int
	 * @param s3 an input-parameter of type int
	 * @param s4 an input-parameter of type int
	 * @param s5 an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_PRINTERSTATUS(boolean waitForPrint, int s0, int s1, int s2, int s3, int s4, int s5) {
		return Dispatch.call(this, "GET_PRINTERSTATUS", new Variant(waitForPrint), new Variant(s0), new Variant(s1), new Variant(s2), new Variant(s3), new Variant(s4), new Variant(s5)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param waitForPrint an input-parameter of type boolean
	 * @param s0 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s1 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s2 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s3 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s4 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param s5 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_PRINTERSTATUS(boolean waitForPrint, int[] s0, int[] s1, int[] s2, int[] s3, int[] s4, int[] s5) {
		Variant vnt_s0 = new Variant();
		if( s0 == null || s0.length == 0 )
			vnt_s0.putNoParam();
		else
			vnt_s0.putIntRef(s0[0]);

		Variant vnt_s1 = new Variant();
		if( s1 == null || s1.length == 0 )
			vnt_s1.putNoParam();
		else
			vnt_s1.putIntRef(s1[0]);

		Variant vnt_s2 = new Variant();
		if( s2 == null || s2.length == 0 )
			vnt_s2.putNoParam();
		else
			vnt_s2.putIntRef(s2[0]);

		Variant vnt_s3 = new Variant();
		if( s3 == null || s3.length == 0 )
			vnt_s3.putNoParam();
		else
			vnt_s3.putIntRef(s3[0]);

		Variant vnt_s4 = new Variant();
		if( s4 == null || s4.length == 0 )
			vnt_s4.putNoParam();
		else
			vnt_s4.putIntRef(s4[0]);

		Variant vnt_s5 = new Variant();
		if( s5 == null || s5.length == 0 )
			vnt_s5.putNoParam();
		else
			vnt_s5.putIntRef(s5[0]);

		boolean result_of_GET_PRINTERSTATUS = Dispatch.call(this, "GET_PRINTERSTATUS", new Variant(waitForPrint), vnt_s0, vnt_s1, vnt_s2, vnt_s3, vnt_s4, vnt_s5).changeType(Variant.VariantBoolean).getBoolean();

		if( s0 != null && s0.length > 0 )
			s0[0] = vnt_s0.toInt();
		if( s1 != null && s1.length > 0 )
			s1[0] = vnt_s1.toInt();
		if( s2 != null && s2.length > 0 )
			s2[0] = vnt_s2.toInt();
		if( s3 != null && s3.length > 0 )
			s3[0] = vnt_s3.toInt();
		if( s4 != null && s4.length > 0 )
			s4[0] = vnt_s4.toInt();
		if( s5 != null && s5.length > 0 )
			s5[0] = vnt_s5.toInt();

		return result_of_GET_PRINTERSTATUS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type byte
	 * @param taxGr an input-parameter of type byte
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean sET_DEPARTMENT_B(byte dept, byte taxGr, String line1, String line2) {
		return Dispatch.call(this, "SET_DEPARTMENT_B", new Variant(dept), new Variant(taxGr), line1, line2).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type byte
	 * @param exitCode an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param sales an input-parameter of type int
	 * @param recSum an input-parameter of type String
	 * @param totSum an input-parameter of type String
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_DEPDATA_B(byte dept, String exitCode, String taxGr, int sales, String recSum, String totSum, String line1, String line2) {
		return Dispatch.call(this, "GET_DEPDATA_B", new Variant(dept), exitCode, taxGr, new Variant(sales), recSum, totSum, line1, line2).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dept an input-parameter of type byte
	 * @param exitCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sales is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param recSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param line1 is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param line2 is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_DEPDATA_B(byte dept, String[] exitCode, String[] taxGr, int[] sales, String[] recSum, String[] totSum, String[] line1, String[] line2) {
		Variant vnt_exitCode = new Variant();
		if( exitCode == null || exitCode.length == 0 )
			vnt_exitCode.putNoParam();
		else
			vnt_exitCode.putStringRef(exitCode[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_sales = new Variant();
		if( sales == null || sales.length == 0 )
			vnt_sales.putNoParam();
		else
			vnt_sales.putIntRef(sales[0]);

		Variant vnt_recSum = new Variant();
		if( recSum == null || recSum.length == 0 )
			vnt_recSum.putNoParam();
		else
			vnt_recSum.putStringRef(recSum[0]);

		Variant vnt_totSum = new Variant();
		if( totSum == null || totSum.length == 0 )
			vnt_totSum.putNoParam();
		else
			vnt_totSum.putStringRef(totSum[0]);

		Variant vnt_line1 = new Variant();
		if( line1 == null || line1.length == 0 )
			vnt_line1.putNoParam();
		else
			vnt_line1.putStringRef(line1[0]);

		Variant vnt_line2 = new Variant();
		if( line2 == null || line2.length == 0 )
			vnt_line2.putNoParam();
		else
			vnt_line2.putStringRef(line2[0]);

		boolean result_of_GET_DEPDATA_B = Dispatch.call(this, "GET_DEPDATA_B", new Variant(dept), vnt_exitCode, vnt_taxGr, vnt_sales, vnt_recSum, vnt_totSum, vnt_line1, vnt_line2).changeType(Variant.VariantBoolean).getBoolean();

		if( exitCode != null && exitCode.length > 0 )
			exitCode[0] = vnt_exitCode.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( sales != null && sales.length > 0 )
			sales[0] = vnt_sales.toInt();
		if( recSum != null && recSum.length > 0 )
			recSum[0] = vnt_recSum.toString();
		if( totSum != null && totSum.length > 0 )
			totSum[0] = vnt_totSum.toString();
		if( line1 != null && line1.length > 0 )
			line1[0] = vnt_line1.toString();
		if( line2 != null && line2.length > 0 )
			line2[0] = vnt_line2.toString();

		return result_of_GET_DEPDATA_B;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param autoPaperCut an input-parameter of type boolean
	 * @param printLOGO an input-parameter of type boolean
	 * @param printDepartment an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean gET_HF_OPTIONS(boolean autoPaperCut, boolean printLOGO, boolean printDepartment) {
		return Dispatch.call(this, "GET_HF_OPTIONS", new Variant(autoPaperCut), new Variant(printLOGO), new Variant(printDepartment)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param autoPaperCut is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param printLOGO is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param printDepartment is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_HF_OPTIONS(boolean[] autoPaperCut, boolean[] printLOGO, boolean[] printDepartment) {
		Variant vnt_autoPaperCut = new Variant();
		if( autoPaperCut == null || autoPaperCut.length == 0 )
			vnt_autoPaperCut.putNoParam();
		else
			vnt_autoPaperCut.putBooleanRef(autoPaperCut[0]);

		Variant vnt_printLOGO = new Variant();
		if( printLOGO == null || printLOGO.length == 0 )
			vnt_printLOGO.putNoParam();
		else
			vnt_printLOGO.putBooleanRef(printLOGO[0]);

		Variant vnt_printDepartment = new Variant();
		if( printDepartment == null || printDepartment.length == 0 )
			vnt_printDepartment.putNoParam();
		else
			vnt_printDepartment.putBooleanRef(printDepartment[0]);

		boolean result_of_GET_HF_OPTIONS = Dispatch.call(this, "GET_HF_OPTIONS", vnt_autoPaperCut, vnt_printLOGO, vnt_printDepartment).changeType(Variant.VariantBoolean).getBoolean();

		if( autoPaperCut != null && autoPaperCut.length > 0 )
			autoPaperCut[0] = vnt_autoPaperCut.toBoolean();
		if( printLOGO != null && printLOGO.length > 0 )
			printLOGO[0] = vnt_printLOGO.toBoolean();
		if( printDepartment != null && printDepartment.length > 0 )
			printDepartment[0] = vnt_printDepartment.toBoolean();

		return result_of_GET_HF_OPTIONS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param autoPaperCut an input-parameter of type boolean
	 * @param printLOGO an input-parameter of type boolean
	 * @param printDepartment an input-parameter of type boolean
	 * @param printEJSmallFont an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean sET_HF_OPTIONS(boolean autoPaperCut, boolean printLOGO, boolean printDepartment, boolean printEJSmallFont) {
		return Dispatch.call(this, "SET_HF_OPTIONS", new Variant(autoPaperCut), new Variant(printLOGO), new Variant(printDepartment), new Variant(printEJSmallFont)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type String
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT_B(int reportKind, boolean clearOpInfo, int closure, String fM_Total, String taxA, String taxB, String taxC, String taxD) {
		return Dispatch.call(this, "DAILY_REPORT_B", new Variant(reportKind), new Variant(clearOpInfo), new Variant(closure), fM_Total, taxA, taxB, taxC, taxD).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT_B(int reportKind, boolean clearOpInfo, int[] closure, String[] fM_Total, String[] taxA, String[] taxB, String[] taxC, String[] taxD) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putStringRef(fM_Total[0]);

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

		boolean result_of_DAILY_REPORT_B = Dispatch.call(this, "DAILY_REPORT_B", new Variant(reportKind), new Variant(clearOpInfo), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toString();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toString();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toString();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toString();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toString();

		return result_of_DAILY_REPORT_B;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param dept an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param percent an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sellQ_B(String line1, String line2, byte dept, double price, double quantity, double percent) {
		return Dispatch.call(this, "SELLQ_B", line1, line2, new Variant(dept), new Variant(price), new Variant(quantity), new Variant(percent)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean getTaxes2(double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "GetTaxes2", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxes2(double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_GetTaxes2 = Dispatch.call(this, "GetTaxes2", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_GetTaxes2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param num an input-parameter of type int
	 * @param cnt an input-parameter of type int
	 * @param line an input-parameter of type int
	 * @param totLines an input-parameter of type int
	 * @param freeBytes an input-parameter of type int
	 * @param totBytes an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL_INFO(int num, int cnt, int line, int totLines, int freeBytes, int totBytes) {
		return Dispatch.call(this, "GET_JOURNAL_INFO", new Variant(num), new Variant(cnt), new Variant(line), new Variant(totLines), new Variant(freeBytes), new Variant(totBytes)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param num is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param cnt is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param line is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param totLines is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param freeBytes is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param totBytes is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL_INFO(int[] num, int[] cnt, int[] line, int[] totLines, int[] freeBytes, int[] totBytes) {
		Variant vnt_num = new Variant();
		if( num == null || num.length == 0 )
			vnt_num.putNoParam();
		else
			vnt_num.putIntRef(num[0]);

		Variant vnt_cnt = new Variant();
		if( cnt == null || cnt.length == 0 )
			vnt_cnt.putNoParam();
		else
			vnt_cnt.putIntRef(cnt[0]);

		Variant vnt_line = new Variant();
		if( line == null || line.length == 0 )
			vnt_line.putNoParam();
		else
			vnt_line.putIntRef(line[0]);

		Variant vnt_totLines = new Variant();
		if( totLines == null || totLines.length == 0 )
			vnt_totLines.putNoParam();
		else
			vnt_totLines.putIntRef(totLines[0]);

		Variant vnt_freeBytes = new Variant();
		if( freeBytes == null || freeBytes.length == 0 )
			vnt_freeBytes.putNoParam();
		else
			vnt_freeBytes.putIntRef(freeBytes[0]);

		Variant vnt_totBytes = new Variant();
		if( totBytes == null || totBytes.length == 0 )
			vnt_totBytes.putNoParam();
		else
			vnt_totBytes.putIntRef(totBytes[0]);

		boolean result_of_GET_JOURNAL_INFO = Dispatch.call(this, "GET_JOURNAL_INFO", vnt_num, vnt_cnt, vnt_line, vnt_totLines, vnt_freeBytes, vnt_totBytes).changeType(Variant.VariantBoolean).getBoolean();

		if( num != null && num.length > 0 )
			num[0] = vnt_num.toInt();
		if( cnt != null && cnt.length > 0 )
			cnt[0] = vnt_cnt.toInt();
		if( line != null && line.length > 0 )
			line[0] = vnt_line.toInt();
		if( totLines != null && totLines.length > 0 )
			totLines[0] = vnt_totLines.toInt();
		if( freeBytes != null && freeBytes.length > 0 )
			freeBytes[0] = vnt_freeBytes.toInt();
		if( totBytes != null && totBytes.length > 0 )
			totBytes[0] = vnt_totBytes.toInt();

		return result_of_GET_JOURNAL_INFO;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param readAndErase an input-parameter of type boolean
	 * @param fileName an input-parameter of type String
	 * @param cRC32 an input-parameter of type String
	 * @param sHA1 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL(boolean readAndErase, String fileName, String cRC32, String sHA1) {
		return Dispatch.call(this, "GET_JOURNAL", new Variant(readAndErase), fileName, cRC32, sHA1).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param readAndErase an input-parameter of type boolean
	 * @param fileName an input-parameter of type String
	 * @param cRC32 is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sHA1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL(boolean readAndErase, String fileName, String[] cRC32, String[] sHA1) {
		Variant vnt_cRC32 = new Variant();
		if( cRC32 == null || cRC32.length == 0 )
			vnt_cRC32.putNoParam();
		else
			vnt_cRC32.putStringRef(cRC32[0]);

		Variant vnt_sHA1 = new Variant();
		if( sHA1 == null || sHA1.length == 0 )
			vnt_sHA1.putNoParam();
		else
			vnt_sHA1.putStringRef(sHA1[0]);

		boolean result_of_GET_JOURNAL = Dispatch.call(this, "GET_JOURNAL", new Variant(readAndErase), fileName, vnt_cRC32, vnt_sHA1).changeType(Variant.VariantBoolean).getBoolean();

		if( cRC32 != null && cRC32.length > 0 )
			cRC32[0] = vnt_cRC32.toString();
		if( sHA1 != null && sHA1.length > 0 )
			sHA1[0] = vnt_sHA1.toString();

		return result_of_GET_JOURNAL;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bcdType an input-parameter of type byte
	 * @param bcdDATA an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean pRINT_BARCODE(byte bcdType, String bcdDATA) {
		return Dispatch.call(this, "PRINT_BARCODE", new Variant(bcdType), bcdDATA).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean cancel_FReceipt() {
		return Dispatch.call(this, "CANCEL_FRECEIPT").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param aBS an input-parameter of type double
	 * @param subTotal an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sUBTOTAL_ABS(boolean toPrint, boolean toDisplay, double aBS, double subTotal, double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "SUBTOTAL_ABS", new Variant(toPrint), new Variant(toDisplay), new Variant(aBS), new Variant(subTotal), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param aBS an input-parameter of type double
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
	 * @return the result is of type boolean
	 */
	public boolean sUBTOTAL_ABS(boolean toPrint, boolean toDisplay, double aBS, double[] subTotal, double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
		Variant vnt_subTotal = new Variant();
		if( subTotal == null || subTotal.length == 0 )
			vnt_subTotal.putNoParam();
		else
			vnt_subTotal.putDoubleRef(subTotal[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		boolean result_of_SUBTOTAL_ABS = Dispatch.call(this, "SUBTOTAL_ABS", new Variant(toPrint), new Variant(toDisplay), new Variant(aBS), vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( subTotal != null && subTotal.length > 0 )
			subTotal[0] = vnt_subTotal.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_SUBTOTAL_ABS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param aBS an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELLQ_ABS(String line1, String line2, byte taxCode, double price, double quantity, double aBS) {
		return Dispatch.call(this, "SELLQ_ABS", line1, line2, new Variant(taxCode), new Variant(price), new Variant(quantity), new Variant(aBS)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param aBS an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELL_EX_ABS(String line1, byte taxCode, double price, double aBS) {
		return Dispatch.call(this, "SELL_EX_ABS", line1, new Variant(taxCode), new Variant(price), new Variant(aBS)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param taxCode an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param aBS an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELL_ABS(String line1, String line2, byte taxCode, double price, double aBS) {
		return Dispatch.call(this, "SELL_ABS", line1, line2, new Variant(taxCode), new Variant(price), new Variant(aBS)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriod_8(double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, java.util.Date dateTime) {
		return Dispatch.callN(this, "GetTaxesByPeriod_8", new Object[] { new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(dateTime)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
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
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriod_8(double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, java.util.Date[] dateTime) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GetTaxesByPeriod_8 = Dispatch.callN(this, "GetTaxesByPeriod_8", new Object[] { vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_dateTime}).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GetTaxesByPeriod_8;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fromDate an input-parameter of type java.util.Date
	 * @param toDate an input-parameter of type java.util.Date
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriodEx_8(java.util.Date fromDate, java.util.Date toDate, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, java.util.Date dateTime) {
		return Dispatch.callN(this, "GetTaxesByPeriodEx_8", new Object[] { new Variant(fromDate), new Variant(toDate), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(dateTime)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param fromDate an input-parameter of type java.util.Date
	 * @param toDate an input-parameter of type java.util.Date
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
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean getTaxesByPeriodEx_8(java.util.Date fromDate, java.util.Date toDate, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, java.util.Date[] dateTime) {
		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GetTaxesByPeriodEx_8 = Dispatch.callN(this, "GetTaxesByPeriodEx_8", new Object[] { new Variant(fromDate), new Variant(toDate), vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_dateTime}).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GetTaxesByPeriodEx_8;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
	 * @param total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean subTotal_8(boolean toPrint, boolean toDisplay, double perc, double total, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH) {
		return Dispatch.callN(this, "SubTotal_8", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(perc), new Variant(total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
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
	 * @return the result is of type boolean
	 */
	public boolean subTotal_8(boolean toPrint, boolean toDisplay, double perc, double[] total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH) {
		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putDoubleRef(total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		boolean result_of_SubTotal_8 = Dispatch.callN(this, "SubTotal_8", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(perc), vnt_total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantBoolean).getBoolean();

		if( total != null && total.length > 0 )
			total[0] = vnt_total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();

		return result_of_SubTotal_8;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure an input-parameter of type int
	 * @param fM_Total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT_8(int reportKind, boolean clearOpInfo, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH) {
		return Dispatch.callN(this, "DAILY_REPORT_8", new Object[] { new Variant(reportKind), new Variant(clearOpInfo), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param reportKind an input-parameter of type int
	 * @param clearOpInfo an input-parameter of type boolean
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
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
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT_8(int reportKind, boolean clearOpInfo, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putIntRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putDoubleRef(fM_Total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		boolean result_of_DAILY_REPORT_8 = Dispatch.callN(this, "DAILY_REPORT_8", new Object[] { new Variant(reportKind), new Variant(clearOpInfo), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantBoolean).getBoolean();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toInt();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();

		return result_of_DAILY_REPORT_8;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param number an input-parameter of type int
	 * @param total an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @param taxF an input-parameter of type double
	 * @param taxG an input-parameter of type double
	 * @param taxH an input-parameter of type double
	 * @param dateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean gET_LFR_Info_8(int number, double total, double taxA, double taxB, double taxC, double taxD, double taxE, double taxF, double taxG, double taxH, java.util.Date dateTime) {
		return Dispatch.callN(this, "GET_LFR_Info_8", new Object[] { new Variant(number), new Variant(total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE), new Variant(taxF), new Variant(taxG), new Variant(taxH), new Variant(dateTime)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param number is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
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
	 * @param dateTime is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_LFR_Info_8(int[] number, double[] total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE, double[] taxF, double[] taxG, double[] taxH, java.util.Date[] dateTime) {
		Variant vnt_number = new Variant();
		if( number == null || number.length == 0 )
			vnt_number.putNoParam();
		else
			vnt_number.putIntRef(number[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putDoubleRef(total[0]);

		Variant vnt_taxA = new Variant();
		if( taxA == null || taxA.length == 0 )
			vnt_taxA.putNoParam();
		else
			vnt_taxA.putDoubleRef(taxA[0]);

		Variant vnt_taxB = new Variant();
		if( taxB == null || taxB.length == 0 )
			vnt_taxB.putNoParam();
		else
			vnt_taxB.putDoubleRef(taxB[0]);

		Variant vnt_taxC = new Variant();
		if( taxC == null || taxC.length == 0 )
			vnt_taxC.putNoParam();
		else
			vnt_taxC.putDoubleRef(taxC[0]);

		Variant vnt_taxD = new Variant();
		if( taxD == null || taxD.length == 0 )
			vnt_taxD.putNoParam();
		else
			vnt_taxD.putDoubleRef(taxD[0]);

		Variant vnt_taxE = new Variant();
		if( taxE == null || taxE.length == 0 )
			vnt_taxE.putNoParam();
		else
			vnt_taxE.putDoubleRef(taxE[0]);

		Variant vnt_taxF = new Variant();
		if( taxF == null || taxF.length == 0 )
			vnt_taxF.putNoParam();
		else
			vnt_taxF.putDoubleRef(taxF[0]);

		Variant vnt_taxG = new Variant();
		if( taxG == null || taxG.length == 0 )
			vnt_taxG.putNoParam();
		else
			vnt_taxG.putDoubleRef(taxG[0]);

		Variant vnt_taxH = new Variant();
		if( taxH == null || taxH.length == 0 )
			vnt_taxH.putNoParam();
		else
			vnt_taxH.putDoubleRef(taxH[0]);

		Variant vnt_dateTime = new Variant();
		if( dateTime == null || dateTime.length == 0 )
			vnt_dateTime.putNoParam();
		else
			vnt_dateTime.putDateRef(dateTime[0]);

		boolean result_of_GET_LFR_Info_8 = Dispatch.callN(this, "GET_LFR_Info_8", new Object[] { vnt_number, vnt_total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, vnt_dateTime}).changeType(Variant.VariantBoolean).getBoolean();

		if( number != null && number.length > 0 )
			number[0] = vnt_number.toInt();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toDouble();
		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();
		if( taxE != null && taxE.length > 0 )
			taxE[0] = vnt_taxE.toDouble();
		if( taxF != null && taxF.length > 0 )
			taxF[0] = vnt_taxF.toDouble();
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toDouble();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toDouble();
		if( dateTime != null && dateTime.length > 0 )
			dateTime[0] = vnt_dateTime.toJavaDate();

		return result_of_GET_LFR_Info_8;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param port an input-parameter of type int
	 * @param boudRate an input-parameter of type int
	 * @param stopBits an input-parameter of type byte
	 * @param parity an input-parameter of type byte
	 * @param byteSize an input-parameter of type byte
	 * @param prnType an input-parameter of type int
	 * @param prnModel an input-parameter of type int
	 */
	public void iNIT_Ex1(int port, int boudRate, byte stopBits, byte parity, byte byteSize, int prnType, int prnModel) {
		Dispatch.call(this, "INIT_Ex1", new Variant(port), new Variant(boudRate), new Variant(stopBits), new Variant(parity), new Variant(byteSize), new Variant(prnType), new Variant(prnModel));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type byte
	 * @param exitCode an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param sales an input-parameter of type int
	 * @param recSum an input-parameter of type String
	 * @param totSales an input-parameter of type String
	 * @param totSum an input-parameter of type String
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_DEPDATA_B2(byte dept, String exitCode, String taxGr, int sales, String recSum, String totSales, String totSum, String line1, String line2) {
		return Dispatch.callN(this, "GET_DEPDATA_B2", new Object[] { new Variant(dept), exitCode, taxGr, new Variant(sales), recSum, totSales, totSum, line1, line2}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dept an input-parameter of type byte
	 * @param exitCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sales is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param recSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totSales is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param totSum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param line1 is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param line2 is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_DEPDATA_B2(byte dept, String[] exitCode, String[] taxGr, int[] sales, String[] recSum, String[] totSales, String[] totSum, String[] line1, String[] line2) {
		Variant vnt_exitCode = new Variant();
		if( exitCode == null || exitCode.length == 0 )
			vnt_exitCode.putNoParam();
		else
			vnt_exitCode.putStringRef(exitCode[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_sales = new Variant();
		if( sales == null || sales.length == 0 )
			vnt_sales.putNoParam();
		else
			vnt_sales.putIntRef(sales[0]);

		Variant vnt_recSum = new Variant();
		if( recSum == null || recSum.length == 0 )
			vnt_recSum.putNoParam();
		else
			vnt_recSum.putStringRef(recSum[0]);

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

		Variant vnt_line1 = new Variant();
		if( line1 == null || line1.length == 0 )
			vnt_line1.putNoParam();
		else
			vnt_line1.putStringRef(line1[0]);

		Variant vnt_line2 = new Variant();
		if( line2 == null || line2.length == 0 )
			vnt_line2.putNoParam();
		else
			vnt_line2.putStringRef(line2[0]);

		boolean result_of_GET_DEPDATA_B2 = Dispatch.callN(this, "GET_DEPDATA_B2", new Object[] { new Variant(dept), vnt_exitCode, vnt_taxGr, vnt_sales, vnt_recSum, vnt_totSales, vnt_totSum, vnt_line1, vnt_line2}).changeType(Variant.VariantBoolean).getBoolean();

		if( exitCode != null && exitCode.length > 0 )
			exitCode[0] = vnt_exitCode.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( sales != null && sales.length > 0 )
			sales[0] = vnt_sales.toInt();
		if( recSum != null && recSum.length > 0 )
			recSum[0] = vnt_recSum.toString();
		if( totSales != null && totSales.length > 0 )
			totSales[0] = vnt_totSales.toString();
		if( totSum != null && totSum.length > 0 )
			totSum[0] = vnt_totSum.toString();
		if( line1 != null && line1.length > 0 )
			line1[0] = vnt_line1.toString();
		if( line2 != null && line2.length > 0 )
			line2[0] = vnt_line2.toString();

		return result_of_GET_DEPDATA_B2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR an input-parameter of type String
	 * @param oUTPUT_STR an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean custom_CMD_1(int cMD, String iNPUT_STR, String oUTPUT_STR) {
		return Dispatch.call(this, "CUSTOM_CMD_1", new Variant(cMD), iNPUT_STR, oUTPUT_STR).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_STR is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean custom_CMD_1(int cMD, String[] iNPUT_STR, String[] oUTPUT_STR) {
		Variant vnt_iNPUT_STR = new Variant();
		if( iNPUT_STR == null || iNPUT_STR.length == 0 )
			vnt_iNPUT_STR.putNoParam();
		else
			vnt_iNPUT_STR.putStringRef(iNPUT_STR[0]);

		Variant vnt_oUTPUT_STR = new Variant();
		if( oUTPUT_STR == null || oUTPUT_STR.length == 0 )
			vnt_oUTPUT_STR.putNoParam();
		else
			vnt_oUTPUT_STR.putStringRef(oUTPUT_STR[0]);

		boolean result_of_CUSTOM_CMD_1 = Dispatch.call(this, "CUSTOM_CMD_1", new Variant(cMD), vnt_iNPUT_STR, vnt_oUTPUT_STR).changeType(Variant.VariantBoolean).getBoolean();

		if( iNPUT_STR != null && iNPUT_STR.length > 0 )
			iNPUT_STR[0] = vnt_iNPUT_STR.toString();
		if( oUTPUT_STR != null && oUTPUT_STR.length > 0 )
			oUTPUT_STR[0] = vnt_oUTPUT_STR.toString();

		return result_of_CUSTOM_CMD_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getLastPrintDoc_2() {
		return Dispatch.call(this, "GetLastPrintDoc_2").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR an input-parameter of type String
	 * @return the result is of type String
	 */
	public String cUSTOM_CMD_2(int cMD, String iNPUT_STR) {
		return Dispatch.call(this, "CUSTOM_CMD_2", new Variant(cMD), iNPUT_STR).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type String
	 */
	public String cUSTOM_CMD_2(int cMD, String[] iNPUT_STR) {
		Variant vnt_iNPUT_STR = new Variant();
		if( iNPUT_STR == null || iNPUT_STR.length == 0 )
			vnt_iNPUT_STR.putNoParam();
		else
			vnt_iNPUT_STR.putStringRef(iNPUT_STR[0]);

		String result_of_CUSTOM_CMD_2 = Dispatch.call(this, "CUSTOM_CMD_2", new Variant(cMD), vnt_iNPUT_STR).toString();

		if( iNPUT_STR != null && iNPUT_STR.length > 0 )
			iNPUT_STR[0] = vnt_iNPUT_STR.toString();

		return result_of_CUSTOM_CMD_2;
	}

}
