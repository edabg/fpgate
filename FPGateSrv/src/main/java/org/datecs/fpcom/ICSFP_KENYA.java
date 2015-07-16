/**
 * JacobGen generated file --- do not edit
 *
 * (http://www.sourceforge.net/projects/jacob-project */
package org.datecs.fpcom;

import com.jacob.com.*;

public class ICSFP_KENYA extends Dispatch {

	public static final String componentName = "FP3530.ICSFP_KENYA";

	public ICSFP_KENYA() {
		super(componentName);
	}

	/**
	* This constructor is used instead of a case operation to
	* turn a Dispatch object into a wider object - it must exist
	* in every wrapper class whose instances may be returned from
	* method calls wrapped in VT_DISPATCH Variants.
	*/
	public ICSFP_KENYA(Dispatch d) {
		// take over the IDispatch pointer
		m_pDispatch = d.m_pDispatch;
		// null out the input's pointer
		d.m_pDispatch = 0;
	}

	public ICSFP_KENYA(String compName) {
		super(compName);
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
	 * @param calc an input-parameter of type boolean
	 * @param fwRev an input-parameter of type String
	 * @param fwDate an input-parameter of type String
	 * @param fwTime an input-parameter of type String
	 * @param chk an input-parameter of type String
	 * @param switches an input-parameter of type String
	 * @param serNum an input-parameter of type String
	 * @param fM an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_DIAGNOSTICINFO(boolean calc, String fwRev, String fwDate, String fwTime, String chk, String switches, String serNum, String fM) {
		return Dispatch.call(this, "GET_DIAGNOSTICINFO", new Variant(calc), fwRev, fwDate, fwTime, chk, switches, serNum, fM).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param calc an input-parameter of type boolean
	 * @param fwRev is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param fwDate is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fwTime is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param chk is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param switches is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param serNum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fM is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_DIAGNOSTICINFO(boolean calc, String[] fwRev, String[] fwDate, String[] fwTime, String[] chk, String[] switches, String[] serNum, String[] fM) {
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

		Variant vnt_chk = new Variant();
		if( chk == null || chk.length == 0 )
			vnt_chk.putNoParam();
		else
			vnt_chk.putStringRef(chk[0]);

		Variant vnt_switches = new Variant();
		if( switches == null || switches.length == 0 )
			vnt_switches.putNoParam();
		else
			vnt_switches.putStringRef(switches[0]);

		Variant vnt_serNum = new Variant();
		if( serNum == null || serNum.length == 0 )
			vnt_serNum.putNoParam();
		else
			vnt_serNum.putStringRef(serNum[0]);

		Variant vnt_fM = new Variant();
		if( fM == null || fM.length == 0 )
			vnt_fM.putNoParam();
		else
			vnt_fM.putStringRef(fM[0]);

		boolean result_of_GET_DIAGNOSTICINFO = Dispatch.call(this, "GET_DIAGNOSTICINFO", new Variant(calc), vnt_fwRev, vnt_fwDate, vnt_fwTime, vnt_chk, vnt_switches, vnt_serNum, vnt_fM).changeType(Variant.VariantBoolean).getBoolean();

		if( fwRev != null && fwRev.length > 0 )
			fwRev[0] = vnt_fwRev.toString();
		if( fwDate != null && fwDate.length > 0 )
			fwDate[0] = vnt_fwDate.toString();
		if( fwTime != null && fwTime.length > 0 )
			fwTime[0] = vnt_fwTime.toString();
		if( chk != null && chk.length > 0 )
			chk[0] = vnt_chk.toString();
		if( switches != null && switches.length > 0 )
			switches[0] = vnt_switches.toString();
		if( serNum != null && serNum.length > 0 )
			serNum[0] = vnt_serNum.toString();
		if( fM != null && fM.length > 0 )
			fM[0] = vnt_fM.toString();

		return result_of_GET_DIAGNOSTICINFO;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String gET_LAST_ERRMSG() {
		return Dispatch.call(this, "GET_LAST_ERRMSG").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type int
	 * @param allReceipt an input-parameter of type int
	 * @param fiscReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean oPEN_FISCALCHECK(int opCode, String opPwd, int tillNmb, int allReceipt, int fiscReceipt) {
		return Dispatch.call(this, "OPEN_FISCALCHECK", new Variant(opCode), opPwd, new Variant(tillNmb), new Variant(allReceipt), new Variant(fiscReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param opCode an input-parameter of type int
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type int
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean oPEN_FISCALCHECK(int opCode, String opPwd, int tillNmb, int[] allReceipt, int[] fiscReceipt) {
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

		boolean result_of_OPEN_FISCALCHECK = Dispatch.call(this, "OPEN_FISCALCHECK", new Variant(opCode), opPwd, new Variant(tillNmb), vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toInt();

		return result_of_OPEN_FISCALCHECK;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param text an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean pRINT_FISCALTXT(String text) {
		return Dispatch.call(this, "PRINT_FISCALTXT", text).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param paidMode an input-parameter of type byte
	 * @param pAmount an input-parameter of type double
	 * @param paidCode an input-parameter of type byte
	 * @param amount an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean total(String line1, String line2, byte paidMode, double pAmount, byte paidCode, double amount) {
		return Dispatch.call(this, "Total", line1, line2, new Variant(paidMode), new Variant(pAmount), new Variant(paidCode), new Variant(amount)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param paidMode an input-parameter of type byte
	 * @param pAmount an input-parameter of type double
	 * @param paidCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean total(String line1, String line2, byte paidMode, double pAmount, byte[] paidCode, double[] amount) {
		Variant vnt_paidCode = new Variant();
		if( paidCode == null || paidCode.length == 0 )
			vnt_paidCode.putNoParam();
		else
			vnt_paidCode.putByteRef(paidCode[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putDoubleRef(amount[0]);

		boolean result_of_Total = Dispatch.call(this, "Total", line1, line2, new Variant(paidMode), new Variant(pAmount), vnt_paidCode, vnt_amount).changeType(Variant.VariantBoolean).getBoolean();

		if( paidCode != null && paidCode.length > 0 )
			paidCode[0] = vnt_paidCode.toByte();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toDouble();

		return result_of_Total;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type int
	 * @param fiscReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean cLOSE_FISCALCHECK(int allReceipt, int fiscReceipt) {
		return Dispatch.call(this, "CLOSE_FISCALCHECK", new Variant(allReceipt), new Variant(fiscReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean cLOSE_FISCALCHECK(int[] allReceipt, int[] fiscReceipt) {
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

		boolean result_of_CLOSE_FISCALCHECK = Dispatch.call(this, "CLOSE_FISCALCHECK", vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toInt();

		return result_of_CLOSE_FISCALCHECK;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quan an input-parameter of type double
	 * @param perc an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sellQ(String l1, String l2, byte taxCd, double price, double quan, double perc) {
		return Dispatch.call(this, "SellQ", l1, l2, new Variant(taxCd), new Variant(price), new Variant(quan), new Variant(perc)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
	 * @param subTotal an input-parameter of type double
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean subTotal(boolean toPrint, boolean toDisplay, double perc, double subTotal, double taxA, double taxB, double taxC, double taxD, double taxE) {
		return Dispatch.callN(this, "SubTotal", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(perc), new Variant(subTotal), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE)}).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type boolean
	 * @param toDisplay an input-parameter of type boolean
	 * @param perc an input-parameter of type double
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
	 * @return the result is of type boolean
	 */
	public boolean subTotal(boolean toPrint, boolean toDisplay, double perc, double[] subTotal, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE) {
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

		boolean result_of_SubTotal = Dispatch.callN(this, "SubTotal", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(perc), vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE}).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_SubTotal;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param count an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean pRINT_DUPLICATE(int count) {
		return Dispatch.call(this, "PRINT_DUPLICATE", new Variant(count)).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT(int reportKind, boolean clearOpInfo, int closure, double fM_Total, double taxA, double taxB, double taxC, double taxD, double taxE) {
		return Dispatch.callN(this, "DAILY_REPORT", new Object[] { new Variant(reportKind), new Variant(clearOpInfo), new Variant(closure), new Variant(fM_Total), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE)}).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @return the result is of type boolean
	 */
	public boolean dAILY_REPORT(int reportKind, boolean clearOpInfo, int[] closure, double[] fM_Total, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE) {
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

		boolean result_of_DAILY_REPORT = Dispatch.callN(this, "DAILY_REPORT", new Object[] { new Variant(reportKind), new Variant(clearOpInfo), vnt_closure, vnt_fM_Total, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE}).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_DAILY_REPORT;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type int
	 * @param errCode an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean oPEN_NONFISCALCHECK(int allReceipt, byte errCode) {
		return Dispatch.call(this, "OPEN_NONFISCALCHECK", new Variant(allReceipt), new Variant(errCode)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param errCode is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean oPEN_NONFISCALCHECK(int[] allReceipt, byte[] errCode) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		Variant vnt_errCode = new Variant();
		if( errCode == null || errCode.length == 0 )
			vnt_errCode.putNoParam();
		else
			vnt_errCode.putByteRef(errCode[0]);

		boolean result_of_OPEN_NONFISCALCHECK = Dispatch.call(this, "OPEN_NONFISCALCHECK", vnt_allReceipt, vnt_errCode).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();
		if( errCode != null && errCode.length > 0 )
			errCode[0] = vnt_errCode.toByte();

		return result_of_OPEN_NONFISCALCHECK;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean cLOSE_NONFISCALCHECK(int allReceipt) {
		return Dispatch.call(this, "CLOSE_NONFISCALCHECK", new Variant(allReceipt)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean cLOSE_NONFISCALCHECK(int[] allReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putIntRef(allReceipt[0]);

		boolean result_of_CLOSE_NONFISCALCHECK = Dispatch.call(this, "CLOSE_NONFISCALCHECK", vnt_allReceipt).changeType(Variant.VariantBoolean).getBoolean();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toInt();

		return result_of_CLOSE_NONFISCALCHECK;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param txt an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean pRINT_NONFISCALTXT(String txt) {
		return Dispatch.call(this, "PRINT_NONFISCALTXT", txt).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line an input-parameter of type String
	 * @param taxCd an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param perc an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELL_EX(String line, byte taxCd, double price, double quantity, double perc) {
		return Dispatch.call(this, "SELL_EX", line, new Variant(taxCd), new Variant(price), new Variant(quantity), new Variant(perc)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param lastDate an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean gET_TAXRATES(double taxA, double taxB, double taxC, double taxD, java.util.Date lastDate) {
		return Dispatch.call(this, "GET_TAXRATES", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(lastDate)).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @param lastDate is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_TAXRATES(double[] taxA, double[] taxB, double[] taxC, double[] taxD, java.util.Date[] lastDate) {
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

		boolean result_of_GET_TAXRATES = Dispatch.call(this, "GET_TAXRATES", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_lastDate).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_GET_TAXRATES;
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
	public boolean gET_TAXRATES_EX(java.util.Date fromDate, java.util.Date toDate, double taxA, double taxB, double taxC, double taxD, java.util.Date lastDate) {
		return Dispatch.call(this, "GET_TAXRATES_EX", new Variant(fromDate), new Variant(toDate), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(lastDate)).changeType(Variant.VariantBoolean).getBoolean();
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
	public boolean gET_TAXRATES_EX(java.util.Date fromDate, java.util.Date toDate, double[] taxA, double[] taxB, double[] taxC, double[] taxD, java.util.Date[] lastDate) {
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

		boolean result_of_GET_TAXRATES_EX = Dispatch.call(this, "GET_TAXRATES_EX", new Variant(fromDate), new Variant(toDate), vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_lastDate).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_GET_TAXRATES_EX;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startRec an input-parameter of type int
	 * @param endRec an input-parameter of type int
	 * @param detailed an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean pRINT_MEMREC(int startRec, int endRec, boolean detailed) {
		return Dispatch.call(this, "PRINT_MEMREC", new Variant(startRec), new Variant(endRec), new Variant(detailed)).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @param monthly an input-parameter of type boolean
	 * @param detailed an input-parameter of type boolean
	 * @param startDate an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean pRINT_MREPORT(boolean monthly, boolean detailed, java.util.Date startDate) {
		return Dispatch.call(this, "PRINT_MREPORT", new Variant(monthly), new Variant(detailed), new Variant(startDate)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean oPERATORS_REPORT() {
		return Dispatch.call(this, "OPERATORS_REPORT").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param all an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean iTEMS_REPORT(boolean all) {
		return Dispatch.call(this, "ITEMS_REPORT", new Variant(all)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lines an input-parameter of type byte
	 * @param option an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean aDVANCE_PAPER(byte lines, byte option) {
		return Dispatch.call(this, "ADVANCE_PAPER", new Variant(lines), new Variant(option)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean tRUNCATE_PAPER() {
		return Dispatch.call(this, "TRUNCATE_PAPER").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dAY an input-parameter of type byte
	 * @param mONTH an input-parameter of type byte
	 * @param yEAR an input-parameter of type byte
	 * @param hOUR an input-parameter of type byte
	 * @param mINUTE an input-parameter of type byte
	 * @param sECOND an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean rEAD_DATETIME(byte dAY, byte mONTH, byte yEAR, byte hOUR, byte mINUTE, byte sECOND) {
		return Dispatch.call(this, "READ_DATETIME", new Variant(dAY), new Variant(mONTH), new Variant(yEAR), new Variant(hOUR), new Variant(mINUTE), new Variant(sECOND)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dAY is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param mONTH is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param yEAR is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param hOUR is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param mINUTE is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param sECOND is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean rEAD_DATETIME(byte[] dAY, byte[] mONTH, byte[] yEAR, byte[] hOUR, byte[] mINUTE, byte[] sECOND) {
		Variant vnt_dAY = new Variant();
		if( dAY == null || dAY.length == 0 )
			vnt_dAY.putNoParam();
		else
			vnt_dAY.putByteRef(dAY[0]);

		Variant vnt_mONTH = new Variant();
		if( mONTH == null || mONTH.length == 0 )
			vnt_mONTH.putNoParam();
		else
			vnt_mONTH.putByteRef(mONTH[0]);

		Variant vnt_yEAR = new Variant();
		if( yEAR == null || yEAR.length == 0 )
			vnt_yEAR.putNoParam();
		else
			vnt_yEAR.putByteRef(yEAR[0]);

		Variant vnt_hOUR = new Variant();
		if( hOUR == null || hOUR.length == 0 )
			vnt_hOUR.putNoParam();
		else
			vnt_hOUR.putByteRef(hOUR[0]);

		Variant vnt_mINUTE = new Variant();
		if( mINUTE == null || mINUTE.length == 0 )
			vnt_mINUTE.putNoParam();
		else
			vnt_mINUTE.putByteRef(mINUTE[0]);

		Variant vnt_sECOND = new Variant();
		if( sECOND == null || sECOND.length == 0 )
			vnt_sECOND.putNoParam();
		else
			vnt_sECOND.putByteRef(sECOND[0]);

		boolean result_of_READ_DATETIME = Dispatch.call(this, "READ_DATETIME", vnt_dAY, vnt_mONTH, vnt_yEAR, vnt_hOUR, vnt_mINUTE, vnt_sECOND).changeType(Variant.VariantBoolean).getBoolean();

		if( dAY != null && dAY.length > 0 )
			dAY[0] = vnt_dAY.toByte();
		if( mONTH != null && mONTH.length > 0 )
			mONTH[0] = vnt_mONTH.toByte();
		if( yEAR != null && yEAR.length > 0 )
			yEAR[0] = vnt_yEAR.toByte();
		if( hOUR != null && hOUR.length > 0 )
			hOUR[0] = vnt_hOUR.toByte();
		if( mINUTE != null && mINUTE.length > 0 )
			mINUTE[0] = vnt_mINUTE.toByte();
		if( sECOND != null && sECOND.length > 0 )
			sECOND[0] = vnt_sECOND.toByte();

		return result_of_READ_DATETIME;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param rec an input-parameter of type int
	 * @param totalA an input-parameter of type double
	 * @param totalB an input-parameter of type double
	 * @param totalC an input-parameter of type double
	 * @param totalD an input-parameter of type double
	 * @param totalE an input-parameter of type double
	 * @param dateStr an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean lAST_FCLOSURE(int rec, double totalA, double totalB, double totalC, double totalD, double totalE, String dateStr) {
		return Dispatch.call(this, "LAST_FCLOSURE", new Variant(rec), new Variant(totalA), new Variant(totalB), new Variant(totalC), new Variant(totalD), new Variant(totalE), dateStr).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param rec is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param totalA is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalB is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalC is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalD is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalE is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param dateStr is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean lAST_FCLOSURE(int[] rec, double[] totalA, double[] totalB, double[] totalC, double[] totalD, double[] totalE, String[] dateStr) {
		Variant vnt_rec = new Variant();
		if( rec == null || rec.length == 0 )
			vnt_rec.putNoParam();
		else
			vnt_rec.putIntRef(rec[0]);

		Variant vnt_totalA = new Variant();
		if( totalA == null || totalA.length == 0 )
			vnt_totalA.putNoParam();
		else
			vnt_totalA.putDoubleRef(totalA[0]);

		Variant vnt_totalB = new Variant();
		if( totalB == null || totalB.length == 0 )
			vnt_totalB.putNoParam();
		else
			vnt_totalB.putDoubleRef(totalB[0]);

		Variant vnt_totalC = new Variant();
		if( totalC == null || totalC.length == 0 )
			vnt_totalC.putNoParam();
		else
			vnt_totalC.putDoubleRef(totalC[0]);

		Variant vnt_totalD = new Variant();
		if( totalD == null || totalD.length == 0 )
			vnt_totalD.putNoParam();
		else
			vnt_totalD.putDoubleRef(totalD[0]);

		Variant vnt_totalE = new Variant();
		if( totalE == null || totalE.length == 0 )
			vnt_totalE.putNoParam();
		else
			vnt_totalE.putDoubleRef(totalE[0]);

		Variant vnt_dateStr = new Variant();
		if( dateStr == null || dateStr.length == 0 )
			vnt_dateStr.putNoParam();
		else
			vnt_dateStr.putStringRef(dateStr[0]);

		boolean result_of_LAST_FCLOSURE = Dispatch.call(this, "LAST_FCLOSURE", vnt_rec, vnt_totalA, vnt_totalB, vnt_totalC, vnt_totalD, vnt_totalE, vnt_dateStr).changeType(Variant.VariantBoolean).getBoolean();

		if( rec != null && rec.length > 0 )
			rec[0] = vnt_rec.toInt();
		if( totalA != null && totalA.length > 0 )
			totalA[0] = vnt_totalA.toDouble();
		if( totalB != null && totalB.length > 0 )
			totalB[0] = vnt_totalB.toDouble();
		if( totalC != null && totalC.length > 0 )
			totalC[0] = vnt_totalC.toDouble();
		if( totalD != null && totalD.length > 0 )
			totalD[0] = vnt_totalD.toDouble();
		if( totalE != null && totalE.length > 0 )
			totalE[0] = vnt_totalE.toDouble();
		if( dateStr != null && dateStr.length > 0 )
			dateStr[0] = vnt_dateStr.toString();

		return result_of_LAST_FCLOSURE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param totalA an input-parameter of type double
	 * @param totalB an input-parameter of type double
	 * @param totalC an input-parameter of type double
	 * @param totalD an input-parameter of type double
	 * @param totalE an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean dAILY_TOTALS(double totalA, double totalB, double totalC, double totalD, double totalE) {
		return Dispatch.call(this, "DAILY_TOTALS", new Variant(totalA), new Variant(totalB), new Variant(totalC), new Variant(totalD), new Variant(totalE)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param totalA is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalB is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalC is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalD is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param totalE is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dAILY_TOTALS(double[] totalA, double[] totalB, double[] totalC, double[] totalD, double[] totalE) {
		Variant vnt_totalA = new Variant();
		if( totalA == null || totalA.length == 0 )
			vnt_totalA.putNoParam();
		else
			vnt_totalA.putDoubleRef(totalA[0]);

		Variant vnt_totalB = new Variant();
		if( totalB == null || totalB.length == 0 )
			vnt_totalB.putNoParam();
		else
			vnt_totalB.putDoubleRef(totalB[0]);

		Variant vnt_totalC = new Variant();
		if( totalC == null || totalC.length == 0 )
			vnt_totalC.putNoParam();
		else
			vnt_totalC.putDoubleRef(totalC[0]);

		Variant vnt_totalD = new Variant();
		if( totalD == null || totalD.length == 0 )
			vnt_totalD.putNoParam();
		else
			vnt_totalD.putDoubleRef(totalD[0]);

		Variant vnt_totalE = new Variant();
		if( totalE == null || totalE.length == 0 )
			vnt_totalE.putNoParam();
		else
			vnt_totalE.putDoubleRef(totalE[0]);

		boolean result_of_DAILY_TOTALS = Dispatch.call(this, "DAILY_TOTALS", vnt_totalA, vnt_totalB, vnt_totalC, vnt_totalD, vnt_totalE).changeType(Variant.VariantBoolean).getBoolean();

		if( totalA != null && totalA.length > 0 )
			totalA[0] = vnt_totalA.toDouble();
		if( totalB != null && totalB.length > 0 )
			totalB[0] = vnt_totalB.toDouble();
		if( totalC != null && totalC.length > 0 )
			totalC[0] = vnt_totalC.toDouble();
		if( totalD != null && totalD.length > 0 )
			totalD[0] = vnt_totalD.toDouble();
		if( totalE != null && totalE.length > 0 )
			totalE[0] = vnt_totalE.toDouble();

		return result_of_DAILY_TOTALS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param logical an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_FREE_FREC(int logical) {
		return Dispatch.call(this, "GET_FREE_FREC", new Variant(logical)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param logical is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_FREE_FREC(int[] logical) {
		Variant vnt_logical = new Variant();
		if( logical == null || logical.length == 0 )
			vnt_logical.putNoParam();
		else
			vnt_logical.putIntRef(logical[0]);

		boolean result_of_GET_FREE_FREC = Dispatch.call(this, "GET_FREE_FREC", vnt_logical).changeType(Variant.VariantBoolean).getBoolean();

		if( logical != null && logical.length > 0 )
			logical[0] = vnt_logical.toInt();

		return result_of_GET_FREE_FREC;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param waitForPrint an input-parameter of type boolean
	 * @param s0 an input-parameter of type byte
	 * @param s1 an input-parameter of type byte
	 * @param s2 an input-parameter of type byte
	 * @param s3 an input-parameter of type byte
	 * @param s4 an input-parameter of type byte
	 * @param s5 an input-parameter of type byte
	 * @return the result is of type boolean
	 */
	public boolean gET_STATUS(boolean waitForPrint, byte s0, byte s1, byte s2, byte s3, byte s4, byte s5) {
		return Dispatch.call(this, "GET_STATUS", new Variant(waitForPrint), new Variant(s0), new Variant(s1), new Variant(s2), new Variant(s3), new Variant(s4), new Variant(s5)).changeType(Variant.VariantBoolean).getBoolean();
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
	public boolean gET_STATUS(boolean waitForPrint, byte[] s0, byte[] s1, byte[] s2, byte[] s3, byte[] s4, byte[] s5) {
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

		boolean result_of_GET_STATUS = Dispatch.call(this, "GET_STATUS", new Variant(waitForPrint), vnt_s0, vnt_s1, vnt_s2, vnt_s3, vnt_s4, vnt_s5).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_GET_STATUS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param currentState an input-parameter of type boolean
	 * @param openReceipt an input-parameter of type boolean
	 * @param receiptType an input-parameter of type byte
	 * @param sellCount an input-parameter of type int
	 * @param amount an input-parameter of type double
	 * @param tender an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean gET_TRANSACTIONSTATUS(boolean currentState, boolean openReceipt, byte receiptType, int sellCount, double amount, double tender) {
		return Dispatch.call(this, "GET_TRANSACTIONSTATUS", new Variant(currentState), new Variant(openReceipt), new Variant(receiptType), new Variant(sellCount), new Variant(amount), new Variant(tender)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param currentState an input-parameter of type boolean
	 * @param openReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param receiptType is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param sellCount is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param tender is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_TRANSACTIONSTATUS(boolean currentState, boolean[] openReceipt, byte[] receiptType, int[] sellCount, double[] amount, double[] tender) {
		Variant vnt_openReceipt = new Variant();
		if( openReceipt == null || openReceipt.length == 0 )
			vnt_openReceipt.putNoParam();
		else
			vnt_openReceipt.putBooleanRef(openReceipt[0]);

		Variant vnt_receiptType = new Variant();
		if( receiptType == null || receiptType.length == 0 )
			vnt_receiptType.putNoParam();
		else
			vnt_receiptType.putByteRef(receiptType[0]);

		Variant vnt_sellCount = new Variant();
		if( sellCount == null || sellCount.length == 0 )
			vnt_sellCount.putNoParam();
		else
			vnt_sellCount.putIntRef(sellCount[0]);

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

		boolean result_of_GET_TRANSACTIONSTATUS = Dispatch.call(this, "GET_TRANSACTIONSTATUS", new Variant(currentState), vnt_openReceipt, vnt_receiptType, vnt_sellCount, vnt_amount, vnt_tender).changeType(Variant.VariantBoolean).getBoolean();

		if( openReceipt != null && openReceipt.length > 0 )
			openReceipt[0] = vnt_openReceipt.toBoolean();
		if( receiptType != null && receiptType.length > 0 )
			receiptType[0] = vnt_receiptType.toByte();
		if( sellCount != null && sellCount.length > 0 )
			sellCount[0] = vnt_sellCount.toInt();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toDouble();
		if( tender != null && tender.length > 0 )
			tender[0] = vnt_tender.toDouble();

		return result_of_GET_TRANSACTIONSTATUS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean gET_TAXES(double taxA, double taxB, double taxC, double taxD) {
		return Dispatch.call(this, "GET_TAXES", new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD)).changeType(Variant.VariantBoolean).getBoolean();
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
	public boolean gET_TAXES(double[] taxA, double[] taxB, double[] taxC, double[] taxD) {
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

		boolean result_of_GET_TAXES = Dispatch.call(this, "GET_TAXES", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD).changeType(Variant.VariantBoolean).getBoolean();

		if( taxA != null && taxA.length > 0 )
			taxA[0] = vnt_taxA.toDouble();
		if( taxB != null && taxB.length > 0 )
			taxB[0] = vnt_taxB.toDouble();
		if( taxC != null && taxC.length > 0 )
			taxC[0] = vnt_taxC.toDouble();
		if( taxD != null && taxD.length > 0 )
			taxD[0] = vnt_taxD.toDouble();

		return result_of_GET_TAXES;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param vATNt an input-parameter of type String
	 * @param pIN an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_TAXNUMBER(String vATNt, String pIN) {
		return Dispatch.call(this, "GET_TAXNUMBER", vATNt, pIN).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param vATNt is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param pIN is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_TAXNUMBER(String[] vATNt, String[] pIN) {
		Variant vnt_vATNt = new Variant();
		if( vATNt == null || vATNt.length == 0 )
			vnt_vATNt.putNoParam();
		else
			vnt_vATNt.putStringRef(vATNt[0]);

		Variant vnt_pIN = new Variant();
		if( pIN == null || pIN.length == 0 )
			vnt_pIN.putNoParam();
		else
			vnt_pIN.putStringRef(pIN[0]);

		boolean result_of_GET_TAXNUMBER = Dispatch.call(this, "GET_TAXNUMBER", vnt_vATNt, vnt_pIN).changeType(Variant.VariantBoolean).getBoolean();

		if( vATNt != null && vATNt.length > 0 )
			vATNt[0] = vnt_vATNt.toString();
		if( pIN != null && pIN.length > 0 )
			pIN[0] = vnt_pIN.toString();

		return result_of_GET_TAXNUMBER;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param canVd an input-parameter of type boolean
	 * @param taxA an input-parameter of type double
	 * @param taxB an input-parameter of type double
	 * @param taxC an input-parameter of type double
	 * @param taxD an input-parameter of type double
	 * @param taxE an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean gET_CRINFO(boolean canVd, double taxA, double taxB, double taxC, double taxD, double taxE) {
		return Dispatch.call(this, "GET_CRINFO", new Variant(canVd), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param canVd is an one-element array which sends the input-parameter
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
	 * @return the result is of type boolean
	 */
	public boolean gET_CRINFO(boolean[] canVd, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE) {
		Variant vnt_canVd = new Variant();
		if( canVd == null || canVd.length == 0 )
			vnt_canVd.putNoParam();
		else
			vnt_canVd.putBooleanRef(canVd[0]);

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

		boolean result_of_GET_CRINFO = Dispatch.call(this, "GET_CRINFO", vnt_canVd, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE).changeType(Variant.VariantBoolean).getBoolean();

		if( canVd != null && canVd.length > 0 )
			canVd[0] = vnt_canVd.toBoolean();
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

		return result_of_GET_CRINFO;
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
	public boolean gET_DAILYINFOEx(double cash, double credit, double debit, double cheque, int closure, int receipt) {
		return Dispatch.call(this, "GET_DAILYINFOEx", new Variant(cash), new Variant(credit), new Variant(debit), new Variant(cheque), new Variant(closure), new Variant(receipt)).changeType(Variant.VariantBoolean).getBoolean();
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
	public boolean gET_DAILYINFOEx(double[] cash, double[] credit, double[] debit, double[] cheque, int[] closure, int[] receipt) {
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

		boolean result_of_GET_DAILYINFOEx = Dispatch.call(this, "GET_DAILYINFOEx", vnt_cash, vnt_credit, vnt_debit, vnt_cheque, vnt_closure, vnt_receipt).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_GET_DAILYINFOEx;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operator an input-parameter of type byte
	 * @param receipts an input-parameter of type int
	 * @param salesNumber an input-parameter of type int
	 * @param salesSum an input-parameter of type double
	 * @param discountNumber an input-parameter of type int
	 * @param discountSum an input-parameter of type double
	 * @param surchargeNumber an input-parameter of type int
	 * @param surchargeSum an input-parameter of type double
	 * @param voidNumber an input-parameter of type int
	 * @param voidSum an input-parameter of type double
	 * @param name an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_OPERATORINFO(byte operator, int receipts, int salesNumber, double salesSum, int discountNumber, double discountSum, int surchargeNumber, double surchargeSum, int voidNumber, double voidSum, String name) {
		return Dispatch.callN(this, "GET_OPERATORINFO", new Object[] { new Variant(operator), new Variant(receipts), new Variant(salesNumber), new Variant(salesSum), new Variant(discountNumber), new Variant(discountSum), new Variant(surchargeNumber), new Variant(surchargeSum), new Variant(voidNumber), new Variant(voidSum), name}).changeType(Variant.VariantBoolean).getBoolean();
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
	public boolean gET_OPERATORINFO(byte operator, int[] receipts, int[] salesNumber, double[] salesSum, int[] discountNumber, double[] discountSum, int[] surchargeNumber, double[] surchargeSum, int[] voidNumber, double[] voidSum, String[] name) {
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
			vnt_salesSum.putDoubleRef(salesSum[0]);

		Variant vnt_discountNumber = new Variant();
		if( discountNumber == null || discountNumber.length == 0 )
			vnt_discountNumber.putNoParam();
		else
			vnt_discountNumber.putIntRef(discountNumber[0]);

		Variant vnt_discountSum = new Variant();
		if( discountSum == null || discountSum.length == 0 )
			vnt_discountSum.putNoParam();
		else
			vnt_discountSum.putDoubleRef(discountSum[0]);

		Variant vnt_surchargeNumber = new Variant();
		if( surchargeNumber == null || surchargeNumber.length == 0 )
			vnt_surchargeNumber.putNoParam();
		else
			vnt_surchargeNumber.putIntRef(surchargeNumber[0]);

		Variant vnt_surchargeSum = new Variant();
		if( surchargeSum == null || surchargeSum.length == 0 )
			vnt_surchargeSum.putNoParam();
		else
			vnt_surchargeSum.putDoubleRef(surchargeSum[0]);

		Variant vnt_voidNumber = new Variant();
		if( voidNumber == null || voidNumber.length == 0 )
			vnt_voidNumber.putNoParam();
		else
			vnt_voidNumber.putIntRef(voidNumber[0]);

		Variant vnt_voidSum = new Variant();
		if( voidSum == null || voidSum.length == 0 )
			vnt_voidSum.putNoParam();
		else
			vnt_voidSum.putDoubleRef(voidSum[0]);

		Variant vnt_name = new Variant();
		if( name == null || name.length == 0 )
			vnt_name.putNoParam();
		else
			vnt_name.putStringRef(name[0]);

		boolean result_of_GET_OPERATORINFO = Dispatch.callN(this, "GET_OPERATORINFO", new Object[] { new Variant(operator), vnt_receipts, vnt_salesNumber, vnt_salesSum, vnt_discountNumber, vnt_discountSum, vnt_surchargeNumber, vnt_surchargeSum, vnt_voidNumber, vnt_voidSum, vnt_name}).changeType(Variant.VariantBoolean).getBoolean();

		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toInt();
		if( salesNumber != null && salesNumber.length > 0 )
			salesNumber[0] = vnt_salesNumber.toInt();
		if( salesSum != null && salesSum.length > 0 )
			salesSum[0] = vnt_salesSum.toDouble();
		if( discountNumber != null && discountNumber.length > 0 )
			discountNumber[0] = vnt_discountNumber.toInt();
		if( discountSum != null && discountSum.length > 0 )
			discountSum[0] = vnt_discountSum.toDouble();
		if( surchargeNumber != null && surchargeNumber.length > 0 )
			surchargeNumber[0] = vnt_surchargeNumber.toInt();
		if( surchargeSum != null && surchargeSum.length > 0 )
			surchargeSum[0] = vnt_surchargeSum.toDouble();
		if( voidNumber != null && voidNumber.length > 0 )
			voidNumber[0] = vnt_voidNumber.toInt();
		if( voidSum != null && voidSum.length > 0 )
			voidSum[0] = vnt_voidSum.toDouble();
		if( name != null && name.length > 0 )
			name[0] = vnt_name.toString();

		return result_of_GET_OPERATORINFO;
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
	 * @param newDateTime an input-parameter of type java.util.Date
	 * @return the result is of type boolean
	 */
	public boolean sET_DATE_TIME(java.util.Date newDateTime) {
		return Dispatch.call(this, "SET_DATE_TIME", new Variant(newDateTime)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bcdType an input-parameter of type byte
	 * @param bcdDATA an input-parameter of type String
	 * @param human_Readable an input-parameter of type boolean
	 * @return the result is of type boolean
	 */
	public boolean pRINT_BARCODE(byte bcdType, String bcdDATA, boolean human_Readable) {
		return Dispatch.call(this, "PRINT_BARCODE", new Variant(bcdType), bcdDATA, new Variant(human_Readable)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean cANCEL_FRECEIPT() {
		return Dispatch.call(this, "CANCEL_FRECEIPT").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param comment an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean tECHNICAL_INTERVENTION(String comment) {
		return Dispatch.call(this, "TECHNICAL_INTERVENTION", comment).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param vATNt an input-parameter of type String
	 * @param pIN an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean sET_TAXNUMBER(String vATNt, String pIN) {
		return Dispatch.call(this, "SET_TAXNUMBER", vATNt, pIN).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param line3 an input-parameter of type String
	 * @param line4 an input-parameter of type String
	 * @param line5 an input-parameter of type String
	 * @param line6 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean sET_HEADER(String line1, String line2, String line3, String line4, String line5, String line6) {
		return Dispatch.call(this, "SET_HEADER", line1, line2, line3, line4, line5, line6).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean sET_FOOTER(String line1, String line2) {
		return Dispatch.call(this, "SET_FOOTER", line1, line2).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @param taxE an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sUBTOTAL_ABS(boolean toPrint, boolean toDisplay, double aBS, double subTotal, double taxA, double taxB, double taxC, double taxD, double taxE) {
		return Dispatch.callN(this, "SUBTOTAL_ABS", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(aBS), new Variant(subTotal), new Variant(taxA), new Variant(taxB), new Variant(taxC), new Variant(taxD), new Variant(taxE)}).changeType(Variant.VariantBoolean).getBoolean();
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
	 * @param taxE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean sUBTOTAL_ABS(boolean toPrint, boolean toDisplay, double aBS, double[] subTotal, double[] taxA, double[] taxB, double[] taxC, double[] taxD, double[] taxE) {
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

		boolean result_of_SUBTOTAL_ABS = Dispatch.callN(this, "SUBTOTAL_ABS", new Object[] { new Variant(toPrint), new Variant(toDisplay), new Variant(aBS), vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE}).changeType(Variant.VariantBoolean).getBoolean();

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

		return result_of_SUBTOTAL_ABS;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param taxCd an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param aBS an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELLQ_ABS(String line1, String line2, byte taxCd, double price, double quantity, double aBS) {
		return Dispatch.call(this, "SELLQ_ABS", line1, line2, new Variant(taxCd), new Variant(price), new Variant(quantity), new Variant(aBS)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param taxCd an input-parameter of type byte
	 * @param price an input-parameter of type double
	 * @param quantity an input-parameter of type double
	 * @param aBS an input-parameter of type double
	 * @return the result is of type boolean
	 */
	public boolean sELL_EX_ABS(String line1, byte taxCd, double price, double quantity, double aBS) {
		return Dispatch.call(this, "SELL_EX_ABS", line1, new Variant(taxCd), new Variant(price), new Variant(quantity), new Variant(aBS)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lineNumber an input-parameter of type int
	 * @param respData an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL_LINE(int lineNumber, String respData) {
		return Dispatch.call(this, "GET_JOURNAL_LINE", new Variant(lineNumber), respData).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param lineNumber an input-parameter of type int
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_JOURNAL_LINE(int lineNumber, String[] respData) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		boolean result_of_GET_JOURNAL_LINE = Dispatch.call(this, "GET_JOURNAL_LINE", new Variant(lineNumber), vnt_respData).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();

		return result_of_GET_JOURNAL_LINE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param respData an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_FIRST_JOURNAL_LINE(String respData) {
		return Dispatch.call(this, "GET_FIRST_JOURNAL_LINE", respData).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_FIRST_JOURNAL_LINE(String[] respData) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		boolean result_of_GET_FIRST_JOURNAL_LINE = Dispatch.call(this, "GET_FIRST_JOURNAL_LINE", vnt_respData).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();

		return result_of_GET_FIRST_JOURNAL_LINE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param respData an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_NEXT_JOURNAL_LINE(String respData) {
		return Dispatch.call(this, "GET_NEXT_JOURNAL_LINE", respData).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_NEXT_JOURNAL_LINE(String[] respData) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		boolean result_of_GET_NEXT_JOURNAL_LINE = Dispatch.call(this, "GET_NEXT_JOURNAL_LINE", vnt_respData).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();

		return result_of_GET_NEXT_JOURNAL_LINE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param mSec an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean dRAWER_KICK_OUT(int mSec) {
		return Dispatch.call(this, "DRAWER_KICK_OUT", new Variant(mSec)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param mSec is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean dRAWER_KICK_OUT(int[] mSec) {
		Variant vnt_mSec = new Variant();
		if( mSec == null || mSec.length == 0 )
			vnt_mSec.putNoParam();
		else
			vnt_mSec.putIntRef(mSec[0]);

		boolean result_of_DRAWER_KICK_OUT = Dispatch.call(this, "DRAWER_KICK_OUT", vnt_mSec).changeType(Variant.VariantBoolean).getBoolean();

		if( mSec != null && mSec.length > 0 )
			mSec[0] = vnt_mSec.toInt();

		return result_of_DRAWER_KICK_OUT;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean eRASE_JOURNAL(String sIGN) {
		return Dispatch.call(this, "ERASE_JOURNAL", sIGN).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param trgtFile an input-parameter of type String
	 * @return the result is of type String
	 */
	public String gET_SHA1(String trgtFile) {
		return Dispatch.call(this, "GET_SHA1", trgtFile).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param trgtFile an input-parameter of type String
	 * @return the result is of type String
	 */
	public String gET_CRC32(String trgtFile) {
		return Dispatch.call(this, "GET_CRC32", trgtFile).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param respDate an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_LAST_FMRD(String respDate) {
		return Dispatch.call(this, "GET_LAST_FMRD", respDate).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param respDate is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_LAST_FMRD(String[] respDate) {
		Variant vnt_respDate = new Variant();
		if( respDate == null || respDate.length == 0 )
			vnt_respDate.putNoParam();
		else
			vnt_respDate.putStringRef(respDate[0]);

		boolean result_of_GET_LAST_FMRD = Dispatch.call(this, "GET_LAST_FMRD", vnt_respDate).changeType(Variant.VariantBoolean).getBoolean();

		if( respDate != null && respDate.length > 0 )
			respDate[0] = vnt_respDate.toString();

		return result_of_GET_LAST_FMRD;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lineCount an input-parameter of type int
	 * @param respData an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean gET_NEXT_JPORTION(int lineCount, String respData) {
		return Dispatch.call(this, "GET_NEXT_JPORTION", new Variant(lineCount), respData).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param lineCount an input-parameter of type int
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_NEXT_JPORTION(int lineCount, String[] respData) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		boolean result_of_GET_NEXT_JPORTION = Dispatch.call(this, "GET_NEXT_JPORTION", new Variant(lineCount), vnt_respData).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();

		return result_of_GET_NEXT_JPORTION;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param respData an input-parameter of type String
	 * @param lineCount an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_FJ_LINE(String respData, int lineCount) {
		return Dispatch.call(this, "GET_FJ_LINE", respData, new Variant(lineCount)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param lineCount is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_FJ_LINE(String[] respData, int[] lineCount) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		Variant vnt_lineCount = new Variant();
		if( lineCount == null || lineCount.length == 0 )
			vnt_lineCount.putNoParam();
		else
			vnt_lineCount.putIntRef(lineCount[0]);

		boolean result_of_GET_FJ_LINE = Dispatch.call(this, "GET_FJ_LINE", vnt_respData, vnt_lineCount).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();
		if( lineCount != null && lineCount.length > 0 )
			lineCount[0] = vnt_lineCount.toInt();

		return result_of_GET_FJ_LINE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param respData an input-parameter of type String
	 * @param lineCount an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_NJ_LINE(String respData, int lineCount) {
		return Dispatch.call(this, "GET_NJ_LINE", respData, new Variant(lineCount)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param respData is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param lineCount is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean gET_NJ_LINE(String[] respData, int[] lineCount) {
		Variant vnt_respData = new Variant();
		if( respData == null || respData.length == 0 )
			vnt_respData.putNoParam();
		else
			vnt_respData.putStringRef(respData[0]);

		Variant vnt_lineCount = new Variant();
		if( lineCount == null || lineCount.length == 0 )
			vnt_lineCount.putNoParam();
		else
			vnt_lineCount.putIntRef(lineCount[0]);

		boolean result_of_GET_NJ_LINE = Dispatch.call(this, "GET_NJ_LINE", vnt_respData, vnt_lineCount).changeType(Variant.VariantBoolean).getBoolean();

		if( respData != null && respData.length > 0 )
			respData[0] = vnt_respData.toString();
		if( lineCount != null && lineCount.length > 0 )
			lineCount[0] = vnt_lineCount.toInt();

		return result_of_GET_NJ_LINE;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR an input-parameter of type String
	 * @param oUTPUT_STR an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean cUSTOM_CMD_1(int cMD, String iNPUT_STR, String oUTPUT_STR) {
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
	public boolean cUSTOM_CMD_1(int cMD, String[] iNPUT_STR, String[] oUTPUT_STR) {
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
	 * @param port an input-parameter of type int
	 * @param boudRate an input-parameter of type int
	 * @param printerModel an input-parameter of type String
	 */
	public void iNIT_Ex1(int port, int boudRate, String printerModel) {
		Dispatch.call(this, "INIT_Ex1", new Variant(port), new Variant(boudRate), printerModel);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean oPEN_CONNECTION() {
		return Dispatch.call(this, "OPEN_CONNECTION").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean cLOSE_CONNECTION() {
		return Dispatch.call(this, "CLOSE_CONNECTION").changeType(Variant.VariantBoolean).getBoolean();
	}

}
