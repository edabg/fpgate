/**
 * JacobGen generated file --- do not edit
 *
 * (http://www.sourceforge.net/projects/jacob-project */
package org.datecs.fpcom;

import com.jacob.com.*;

public class ICS_BGR_FP550_KL extends Dispatch {

	public static final String componentName = "FP3530.ICS_BGR_FP550_KL";

	public ICS_BGR_FP550_KL() {
		super(componentName);
	}

	/**
	* This constructor is used instead of a case operation to
	* turn a Dispatch object into a wider object - it must exist
	* in every wrapper class whose instances may be returned from
	* method calls wrapped in VT_DISPATCH Variants.
	*/
	public ICS_BGR_FP550_KL(Dispatch d) {
		// take over the IDispatch pointer
		m_pDispatch = d.m_pDispatch;
		// null out the input's pointer
		d.m_pDispatch = 0;
	}

	public ICS_BGR_FP550_KL(String compName) {
		super(compName);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param port an input-parameter of type int
	 * @param boudRate an input-parameter of type int
	 */
	public void iNIT_Ex1(int port, int boudRate) {
		Dispatch.call(this, "INIT_Ex1", new Variant(port), new Variant(boudRate));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR an input-parameter of type String
	 * @param oUTPUT_STR an input-parameter of type String
	 * @return the result is of type boolean
	 */
	public boolean cUSTOM_CMD(int cMD, String iNPUT_STR, String oUTPUT_STR) {
		return Dispatch.call(this, "CUSTOM_CMD", new Variant(cMD), iNPUT_STR, oUTPUT_STR).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cMD an input-parameter of type int
	 * @param iNPUT_STR an input-parameter of type String
	 * @param oUTPUT_STR is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type boolean
	 */
	public boolean cUSTOM_CMD(int cMD, String iNPUT_STR, String[] oUTPUT_STR) {
		Variant vnt_oUTPUT_STR = new Variant();
		if( oUTPUT_STR == null || oUTPUT_STR.length == 0 )
			vnt_oUTPUT_STR.putNoParam();
		else
			vnt_oUTPUT_STR.putStringRef(oUTPUT_STR[0]);

		boolean result_of_CUSTOM_CMD = Dispatch.call(this, "CUSTOM_CMD", new Variant(cMD), iNPUT_STR, vnt_oUTPUT_STR).changeType(Variant.VariantBoolean).getBoolean();

		if( oUTPUT_STR != null && oUTPUT_STR.length > 0 )
			oUTPUT_STR[0] = vnt_oUTPUT_STR.toString();

		return result_of_CUSTOM_CMD;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_33_0_0() {
		return Dispatch.call(this, "CMD_33_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param shortEndDate an input-parameter of type String
	 * @param left an input-parameter of type String
	 * @param regDtTm an input-parameter of type String
	 * @param eIK an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_34_0_0(String toPrint, String shortEndDate, String left, String regDtTm, String eIK, String endDate) {
		return Dispatch.call(this, "CMD_34_0_0", toPrint, shortEndDate, left, regDtTm, eIK, endDate).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param shortEndDate is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param left is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param regDtTm is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param eIK is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param endDate is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_34_0_0(String toPrint, String[] shortEndDate, String[] left, String[] regDtTm, String[] eIK, String[] endDate) {
		Variant vnt_shortEndDate = new Variant();
		if( shortEndDate == null || shortEndDate.length == 0 )
			vnt_shortEndDate.putNoParam();
		else
			vnt_shortEndDate.putStringRef(shortEndDate[0]);

		Variant vnt_left = new Variant();
		if( left == null || left.length == 0 )
			vnt_left.putNoParam();
		else
			vnt_left.putStringRef(left[0]);

		Variant vnt_regDtTm = new Variant();
		if( regDtTm == null || regDtTm.length == 0 )
			vnt_regDtTm.putNoParam();
		else
			vnt_regDtTm.putStringRef(regDtTm[0]);

		Variant vnt_eIK = new Variant();
		if( eIK == null || eIK.length == 0 )
			vnt_eIK.putNoParam();
		else
			vnt_eIK.putStringRef(eIK[0]);

		Variant vnt_endDate = new Variant();
		if( endDate == null || endDate.length == 0 )
			vnt_endDate.putNoParam();
		else
			vnt_endDate.putStringRef(endDate[0]);

		int result_of_CMD_34_0_0 = Dispatch.call(this, "CMD_34_0_0", toPrint, vnt_shortEndDate, vnt_left, vnt_regDtTm, vnt_eIK, vnt_endDate).changeType(Variant.VariantInt).getInt();

		if( shortEndDate != null && shortEndDate.length > 0 )
			shortEndDate[0] = vnt_shortEndDate.toString();
		if( left != null && left.length > 0 )
			left[0] = vnt_left.toString();
		if( regDtTm != null && regDtTm.length > 0 )
			regDtTm[0] = vnt_regDtTm.toString();
		if( eIK != null && eIK.length > 0 )
			eIK[0] = vnt_eIK.toString();
		if( endDate != null && endDate.length > 0 )
			endDate[0] = vnt_endDate.toString();

		return result_of_CMD_34_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param theText an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_35_0_0(String theText) {
		return Dispatch.call(this, "CMD_35_0_0", theText).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type String
	 * @param errCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_38_0_0(String allReceipt, String errCode) {
		return Dispatch.call(this, "CMD_38_0_0", allReceipt, errCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param errCode is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_38_0_0(String[] allReceipt, String[] errCode) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putStringRef(allReceipt[0]);

		Variant vnt_errCode = new Variant();
		if( errCode == null || errCode.length == 0 )
			vnt_errCode.putNoParam();
		else
			vnt_errCode.putStringRef(errCode[0]);

		int result_of_CMD_38_0_0 = Dispatch.call(this, "CMD_38_0_0", vnt_allReceipt, vnt_errCode).changeType(Variant.VariantInt).getInt();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toString();
		if( errCode != null && errCode.length > 0 )
			errCode[0] = vnt_errCode.toString();

		return result_of_CMD_38_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_39_0_0(String allReceipt) {
		return Dispatch.call(this, "CMD_39_0_0", allReceipt).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_39_0_0(String[] allReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putStringRef(allReceipt[0]);

		int result_of_CMD_39_0_0 = Dispatch.call(this, "CMD_39_0_0", vnt_allReceipt).changeType(Variant.VariantInt).getInt();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toString();

		return result_of_CMD_39_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_41_0_0() {
		return Dispatch.call(this, "CMD_41_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param theText an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_42_0_0(String theText) {
		return Dispatch.call(this, "CMD_42_0_0", theText).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param iTEM_INDEX an input-parameter of type String
	 * @param dATA_VALUE an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_43_0_0(String iTEM_INDEX, String dATA_VALUE) {
		return Dispatch.call(this, "CMD_43_0_0", iTEM_INDEX, dATA_VALUE).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param iNP_STR an input-parameter of type String
	 * @param oUTP_STR an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_43_0_1(String iNP_STR, String oUTP_STR) {
		return Dispatch.call(this, "CMD_43_0_1", iNP_STR, oUTP_STR).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param iNP_STR an input-parameter of type String
	 * @param oUTP_STR is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_43_0_1(String iNP_STR, String[] oUTP_STR) {
		Variant vnt_oUTP_STR = new Variant();
		if( oUTP_STR == null || oUTP_STR.length == 0 )
			vnt_oUTP_STR.putNoParam();
		else
			vnt_oUTP_STR.putStringRef(oUTP_STR[0]);

		int result_of_CMD_43_0_1 = Dispatch.call(this, "CMD_43_0_1", iNP_STR, vnt_oUTP_STR).changeType(Variant.VariantInt).getInt();

		if( oUTP_STR != null && oUTP_STR.length > 0 )
			oUTP_STR[0] = vnt_oUTP_STR.toString();

		return result_of_CMD_43_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param tRGT_LINES an input-parameter of type String
	 * @param option an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_44_0_0(String tRGT_LINES, String option) {
		return Dispatch.call(this, "CMD_44_0_0", tRGT_LINES, option).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_45_0_0() {
		return Dispatch.call(this, "CMD_45_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param tRGT_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_47_0_0(String tRGT_TEXT) {
		return Dispatch.call(this, "CMD_47_0_0", tRGT_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type String
	 * @param allReceipt an input-parameter of type String
	 * @param fiscReceipt an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_48_0_0(String opCode, String opPwd, String tillNmb, String allReceipt, String fiscReceipt) {
		return Dispatch.call(this, "CMD_48_0_0", opCode, opPwd, tillNmb, allReceipt, fiscReceipt).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type String
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_48_0_0(String opCode, String opPwd, String tillNmb, String[] allReceipt, String[] fiscReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putStringRef(allReceipt[0]);

		Variant vnt_fiscReceipt = new Variant();
		if( fiscReceipt == null || fiscReceipt.length == 0 )
			vnt_fiscReceipt.putNoParam();
		else
			vnt_fiscReceipt.putStringRef(fiscReceipt[0]);

		int result_of_CMD_48_0_0 = Dispatch.call(this, "CMD_48_0_0", opCode, opPwd, tillNmb, vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantInt).getInt();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toString();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toString();

		return result_of_CMD_48_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type String
	 * @param allReceipt an input-parameter of type String
	 * @param fiscReceipt an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_48_0_1(String opCode, String opPwd, String tillNmb, String allReceipt, String fiscReceipt) {
		return Dispatch.call(this, "CMD_48_0_1", opCode, opPwd, tillNmb, allReceipt, fiscReceipt).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param opPwd an input-parameter of type String
	 * @param tillNmb an input-parameter of type String
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_48_0_1(String opCode, String opPwd, String tillNmb, String[] allReceipt, String[] fiscReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putStringRef(allReceipt[0]);

		Variant vnt_fiscReceipt = new Variant();
		if( fiscReceipt == null || fiscReceipt.length == 0 )
			vnt_fiscReceipt.putNoParam();
		else
			vnt_fiscReceipt.putStringRef(fiscReceipt[0]);

		int result_of_CMD_48_0_1 = Dispatch.call(this, "CMD_48_0_1", opCode, opPwd, tillNmb, vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantInt).getInt();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toString();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toString();

		return result_of_CMD_48_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_0_0(String l1, String l2, String taxCd, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_0_0", l1, l2, taxCd, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_0_1(String l1, String taxCd, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_0_1", l1, taxCd, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_1_0(String l1, String l2, String taxCd, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_1_0", l1, l2, taxCd, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_1_1(String l1, String taxCd, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_1_1", l1, taxCd, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_1_2(String l2, String taxCd, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_1_2", l2, taxCd, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_2_0(String l1, String l2, String taxCd, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_2_0", l1, l2, taxCd, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_2_1(String l1, String taxCd, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_2_1", l1, taxCd, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_2_2(String l2, String taxCd, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_2_2", l2, taxCd, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_3_0(String l1, String l2, String dept, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_3_0", l1, l2, dept, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_3_1(String l1, String dept, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_3_1", l1, dept, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_3_2(String l2, String dept, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_3_2", l2, dept, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_4_0(String l1, String l2, String dept, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_4_0", l1, l2, dept, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_4_1(String l1, String dept, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_4_1", l1, dept, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_4_2(String l2, String dept, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_4_2", l2, dept, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_5_0(String l1, String l2, String dept, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_5_0", l1, l2, dept, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_5_1(String l1, String dept, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_5_1", l1, dept, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_5_2(String l2, String dept, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_5_2", l2, dept, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param aA an input-parameter of type String
	 * @param bB an input-parameter of type String
	 * @param cC an input-parameter of type String
	 * @param dD an input-parameter of type String
	 * @param eE an input-parameter of type String
	 * @param fF an input-parameter of type String
	 * @param gG an input-parameter of type String
	 * @param hH an input-parameter of type String
	 * @param dDMMYY an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_50_0_0(String startDate, String endDate, String f_RESULT, String aA, String bB, String cC, String dD, String eE, String fF, String gG, String hH, String dDMMYY) {
		return Dispatch.callN(this, "CMD_50_0_0", new Object[] { startDate, endDate, f_RESULT, aA, bB, cC, dD, eE, fF, gG, hH, dDMMYY}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param aA is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param bB is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param cC is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param dD is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param eE is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param fF is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param gG is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param hH is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param dDMMYY is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_50_0_0(String startDate, String endDate, String[] f_RESULT, String[] aA, String[] bB, String[] cC, String[] dD, String[] eE, String[] fF, String[] gG, String[] hH, String[] dDMMYY) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_aA = new Variant();
		if( aA == null || aA.length == 0 )
			vnt_aA.putNoParam();
		else
			vnt_aA.putStringRef(aA[0]);

		Variant vnt_bB = new Variant();
		if( bB == null || bB.length == 0 )
			vnt_bB.putNoParam();
		else
			vnt_bB.putStringRef(bB[0]);

		Variant vnt_cC = new Variant();
		if( cC == null || cC.length == 0 )
			vnt_cC.putNoParam();
		else
			vnt_cC.putStringRef(cC[0]);

		Variant vnt_dD = new Variant();
		if( dD == null || dD.length == 0 )
			vnt_dD.putNoParam();
		else
			vnt_dD.putStringRef(dD[0]);

		Variant vnt_eE = new Variant();
		if( eE == null || eE.length == 0 )
			vnt_eE.putNoParam();
		else
			vnt_eE.putStringRef(eE[0]);

		Variant vnt_fF = new Variant();
		if( fF == null || fF.length == 0 )
			vnt_fF.putNoParam();
		else
			vnt_fF.putStringRef(fF[0]);

		Variant vnt_gG = new Variant();
		if( gG == null || gG.length == 0 )
			vnt_gG.putNoParam();
		else
			vnt_gG.putStringRef(gG[0]);

		Variant vnt_hH = new Variant();
		if( hH == null || hH.length == 0 )
			vnt_hH.putNoParam();
		else
			vnt_hH.putStringRef(hH[0]);

		Variant vnt_dDMMYY = new Variant();
		if( dDMMYY == null || dDMMYY.length == 0 )
			vnt_dDMMYY.putNoParam();
		else
			vnt_dDMMYY.putStringRef(dDMMYY[0]);

		int result_of_CMD_50_0_0 = Dispatch.callN(this, "CMD_50_0_0", new Object[] { startDate, endDate, vnt_f_RESULT, vnt_aA, vnt_bB, vnt_cC, vnt_dD, vnt_eE, vnt_fF, vnt_gG, vnt_hH, vnt_dDMMYY}).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( aA != null && aA.length > 0 )
			aA[0] = vnt_aA.toString();
		if( bB != null && bB.length > 0 )
			bB[0] = vnt_bB.toString();
		if( cC != null && cC.length > 0 )
			cC[0] = vnt_cC.toString();
		if( dD != null && dD.length > 0 )
			dD[0] = vnt_dD.toString();
		if( eE != null && eE.length > 0 )
			eE[0] = vnt_eE.toString();
		if( fF != null && fF.length > 0 )
			fF[0] = vnt_fF.toString();
		if( gG != null && gG.length > 0 )
			gG[0] = vnt_gG.toString();
		if( hH != null && hH.length > 0 )
			hH[0] = vnt_hH.toString();
		if( dDMMYY != null && dDMMYY.length > 0 )
			dDMMYY[0] = vnt_dDMMYY.toString();

		return result_of_CMD_50_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
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
	public int cMD_51_0_0(String toPrint, String toDisplay, String subTotal, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_51_0_0", new Object[] { toPrint, toDisplay, subTotal, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
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
	public int cMD_51_0_0(String toPrint, String toDisplay, String[] subTotal, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
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

		int result_of_CMD_51_0_0 = Dispatch.callN(this, "CMD_51_0_0", new Object[] { toPrint, toDisplay, vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

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

		return result_of_CMD_51_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
	 * @param perc an input-parameter of type String
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
	public int cMD_51_0_1(String toPrint, String toDisplay, String perc, String subTotal, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_51_0_1", new Object[] { toPrint, toDisplay, perc, subTotal, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
	 * @param perc an input-parameter of type String
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
	public int cMD_51_0_1(String toPrint, String toDisplay, String perc, String[] subTotal, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
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

		int result_of_CMD_51_0_1 = Dispatch.callN(this, "CMD_51_0_1", new Object[] { toPrint, toDisplay, perc, vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

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

		return result_of_CMD_51_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
	 * @param absSum an input-parameter of type String
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
	public int cMD_51_0_2(String toPrint, String toDisplay, String absSum, String subTotal, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_51_0_2", new Object[] { toPrint, toDisplay, absSum, subTotal, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param toPrint an input-parameter of type String
	 * @param toDisplay an input-parameter of type String
	 * @param absSum an input-parameter of type String
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
	public int cMD_51_0_2(String toPrint, String toDisplay, String absSum, String[] subTotal, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
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

		int result_of_CMD_51_0_2 = Dispatch.callN(this, "CMD_51_0_2", new Object[] { toPrint, toDisplay, absSum, vnt_subTotal, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

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

		return result_of_CMD_51_0_2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_0_0(String l1, String taxCd, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_52_0_0", l1, taxCd, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_0_1(String l1, String taxCd, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_52_0_1", l1, taxCd, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_0_2(String l1, String taxCd, String price, String qwan) {
		return Dispatch.call(this, "CMD_52_0_2", l1, taxCd, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_1_0(String l1, String dept, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_52_1_0", l1, dept, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_1_1(String l1, String dept, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_52_1_1", l1, dept, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_52_1_2(String l1, String dept, String price, String qwan) {
		return Dispatch.call(this, "CMD_52_1_2", l1, dept, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param paidMode an input-parameter of type String
	 * @param amount_In an input-parameter of type String
	 * @param paidCode an input-parameter of type String
	 * @param amount_Out an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_53_0_0(String line1, String line2, String paidMode, String amount_In, String paidCode, String amount_Out) {
		return Dispatch.call(this, "CMD_53_0_0", line1, line2, paidMode, amount_In, paidCode, amount_Out).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @param paidMode an input-parameter of type String
	 * @param amount_In an input-parameter of type String
	 * @param paidCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param amount_Out is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_53_0_0(String line1, String line2, String paidMode, String amount_In, String[] paidCode, String[] amount_Out) {
		Variant vnt_paidCode = new Variant();
		if( paidCode == null || paidCode.length == 0 )
			vnt_paidCode.putNoParam();
		else
			vnt_paidCode.putStringRef(paidCode[0]);

		Variant vnt_amount_Out = new Variant();
		if( amount_Out == null || amount_Out.length == 0 )
			vnt_amount_Out.putNoParam();
		else
			vnt_amount_Out.putStringRef(amount_Out[0]);

		int result_of_CMD_53_0_0 = Dispatch.call(this, "CMD_53_0_0", line1, line2, paidMode, amount_In, vnt_paidCode, vnt_amount_Out).changeType(Variant.VariantInt).getInt();

		if( paidCode != null && paidCode.length > 0 )
			paidCode[0] = vnt_paidCode.toString();
		if( amount_Out != null && amount_Out.length > 0 )
			amount_Out[0] = vnt_amount_Out.toString();

		return result_of_CMD_53_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param paidMode an input-parameter of type String
	 * @param amount_In an input-parameter of type String
	 * @param paidCode an input-parameter of type String
	 * @param amount_Out an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_53_0_1(String paidMode, String amount_In, String paidCode, String amount_Out) {
		return Dispatch.call(this, "CMD_53_0_1", paidMode, amount_In, paidCode, amount_Out).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param paidMode an input-parameter of type String
	 * @param amount_In an input-parameter of type String
	 * @param paidCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param amount_Out is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_53_0_1(String paidMode, String amount_In, String[] paidCode, String[] amount_Out) {
		Variant vnt_paidCode = new Variant();
		if( paidCode == null || paidCode.length == 0 )
			vnt_paidCode.putNoParam();
		else
			vnt_paidCode.putStringRef(paidCode[0]);

		Variant vnt_amount_Out = new Variant();
		if( amount_Out == null || amount_Out.length == 0 )
			vnt_amount_Out.putNoParam();
		else
			vnt_amount_Out.putStringRef(amount_Out[0]);

		int result_of_CMD_53_0_1 = Dispatch.call(this, "CMD_53_0_1", paidMode, amount_In, vnt_paidCode, vnt_amount_Out).changeType(Variant.VariantInt).getInt();

		if( paidCode != null && paidCode.length > 0 )
			paidCode[0] = vnt_paidCode.toString();
		if( amount_Out != null && amount_Out.length > 0 )
			amount_Out[0] = vnt_amount_Out.toString();

		return result_of_CMD_53_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param input_Text an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_54_0_0(String input_Text) {
		return Dispatch.call(this, "CMD_54_0_0", input_Text).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param allReceipt an input-parameter of type String
	 * @param fiscReceipt an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_56_0_0(String allReceipt, String fiscReceipt) {
		return Dispatch.call(this, "CMD_56_0_0", allReceipt, fiscReceipt).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param allReceipt is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param fiscReceipt is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_56_0_0(String[] allReceipt, String[] fiscReceipt) {
		Variant vnt_allReceipt = new Variant();
		if( allReceipt == null || allReceipt.length == 0 )
			vnt_allReceipt.putNoParam();
		else
			vnt_allReceipt.putStringRef(allReceipt[0]);

		Variant vnt_fiscReceipt = new Variant();
		if( fiscReceipt == null || fiscReceipt.length == 0 )
			vnt_fiscReceipt.putNoParam();
		else
			vnt_fiscReceipt.putStringRef(fiscReceipt[0]);

		int result_of_CMD_56_0_0 = Dispatch.call(this, "CMD_56_0_0", vnt_allReceipt, vnt_fiscReceipt).changeType(Variant.VariantInt).getInt();

		if( allReceipt != null && allReceipt.length > 0 )
			allReceipt[0] = vnt_allReceipt.toString();
		if( fiscReceipt != null && fiscReceipt.length > 0 )
			fiscReceipt[0] = vnt_fiscReceipt.toString();

		return result_of_CMD_56_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bulstat an input-parameter of type String
	 * @param seller an input-parameter of type String
	 * @param receiver an input-parameter of type String
	 * @param clientName an input-parameter of type String
	 * @param taxNo an input-parameter of type String
	 * @param address1 an input-parameter of type String
	 * @param address2 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_57_0_0(String bulstat, String seller, String receiver, String clientName, String taxNo, String address1, String address2) {
		return Dispatch.call(this, "CMD_57_0_0", bulstat, seller, receiver, clientName, taxNo, address1, address2).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bulstat an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_57_0_1(String bulstat) {
		return Dispatch.call(this, "CMD_57_0_1", bulstat).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_0_0(String sIGN, String pLU, String dept, String qwan, String perc) {
		return Dispatch.call(this, "CMD_58_0_0", sIGN, pLU, dept, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_0_1(String sIGN, String pLU, String dept, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_58_0_1", sIGN, pLU, dept, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_0_2(String sIGN, String pLU, String dept, String qwan) {
		return Dispatch.call(this, "CMD_58_0_2", sIGN, pLU, dept, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_1_0(String sIGN, String pLU, String qwan, String perc) {
		return Dispatch.call(this, "CMD_58_1_0", sIGN, pLU, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_1_1(String sIGN, String pLU, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_58_1_1", sIGN, pLU, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_1_2(String sIGN, String pLU, String qwan) {
		return Dispatch.call(this, "CMD_58_1_2", sIGN, pLU, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_2_0(String sIGN, String pLU, String dept, String qwan, String perc) {
		return Dispatch.call(this, "CMD_58_2_0", sIGN, pLU, dept, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_2_1(String sIGN, String pLU, String dept, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_58_2_1", sIGN, pLU, dept, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_2_2(String sIGN, String pLU, String dept, String qwan) {
		return Dispatch.call(this, "CMD_58_2_2", sIGN, pLU, dept, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_3_0(String sIGN, String pLU, String qwan, String perc) {
		return Dispatch.call(this, "CMD_58_3_0", sIGN, pLU, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_3_1(String sIGN, String pLU, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_58_3_1", sIGN, pLU, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sIGN an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_58_3_2(String sIGN, String pLU, String qwan) {
		return Dispatch.call(this, "CMD_58_3_2", sIGN, pLU, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_60_0_0() {
		return Dispatch.call(this, "CMD_60_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param tRGT_DATE an input-parameter of type String
	 * @param tRGT_TIME an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_61_0_0(String tRGT_DATE, String tRGT_TIME) {
		return Dispatch.call(this, "CMD_61_0_0", tRGT_DATE, tRGT_TIME).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fP_ANSWER an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_62_0_0(String fP_ANSWER) {
		return Dispatch.call(this, "CMD_62_0_0", fP_ANSWER).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param fP_ANSWER is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_62_0_0(String[] fP_ANSWER) {
		Variant vnt_fP_ANSWER = new Variant();
		if( fP_ANSWER == null || fP_ANSWER.length == 0 )
			vnt_fP_ANSWER.putNoParam();
		else
			vnt_fP_ANSWER.putStringRef(fP_ANSWER[0]);

		int result_of_CMD_62_0_0 = Dispatch.call(this, "CMD_62_0_0", vnt_fP_ANSWER).changeType(Variant.VariantInt).getInt();

		if( fP_ANSWER != null && fP_ANSWER.length > 0 )
			fP_ANSWER[0] = vnt_fP_ANSWER.toString();

		return result_of_CMD_62_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dD an input-parameter of type String
	 * @param mM an input-parameter of type String
	 * @param yY an input-parameter of type String
	 * @param hH an input-parameter of type String
	 * @param mMM an input-parameter of type String
	 * @param sS an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_62_0_1(String dD, String mM, String yY, String hH, String mMM, String sS) {
		return Dispatch.call(this, "CMD_62_0_1", dD, mM, yY, hH, mMM, sS).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dD is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param mM is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param yY is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param hH is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param mMM is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param sS is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_62_0_1(String[] dD, String[] mM, String[] yY, String[] hH, String[] mMM, String[] sS) {
		Variant vnt_dD = new Variant();
		if( dD == null || dD.length == 0 )
			vnt_dD.putNoParam();
		else
			vnt_dD.putStringRef(dD[0]);

		Variant vnt_mM = new Variant();
		if( mM == null || mM.length == 0 )
			vnt_mM.putNoParam();
		else
			vnt_mM.putStringRef(mM[0]);

		Variant vnt_yY = new Variant();
		if( yY == null || yY.length == 0 )
			vnt_yY.putNoParam();
		else
			vnt_yY.putStringRef(yY[0]);

		Variant vnt_hH = new Variant();
		if( hH == null || hH.length == 0 )
			vnt_hH.putNoParam();
		else
			vnt_hH.putStringRef(hH[0]);

		Variant vnt_mMM = new Variant();
		if( mMM == null || mMM.length == 0 )
			vnt_mMM.putNoParam();
		else
			vnt_mMM.putStringRef(mMM[0]);

		Variant vnt_sS = new Variant();
		if( sS == null || sS.length == 0 )
			vnt_sS.putNoParam();
		else
			vnt_sS.putStringRef(sS[0]);

		int result_of_CMD_62_0_1 = Dispatch.call(this, "CMD_62_0_1", vnt_dD, vnt_mM, vnt_yY, vnt_hH, vnt_mMM, vnt_sS).changeType(Variant.VariantInt).getInt();

		if( dD != null && dD.length > 0 )
			dD[0] = vnt_dD.toString();
		if( mM != null && mM.length > 0 )
			mM[0] = vnt_mM.toString();
		if( yY != null && yY.length > 0 )
			yY[0] = vnt_yY.toString();
		if( hH != null && hH.length > 0 )
			hH[0] = vnt_hH.toString();
		if( mMM != null && mMM.length > 0 )
			mMM[0] = vnt_mMM.toString();
		if( sS != null && sS.length > 0 )
			sS[0] = vnt_sS.toString();

		return result_of_CMD_62_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_63_0_0() {
		return Dispatch.call(this, "CMD_63_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errCode an input-parameter of type String
	 * @param lastFiscNum an input-parameter of type String
	 * @param totA an input-parameter of type String
	 * @param totB an input-parameter of type String
	 * @param totC an input-parameter of type String
	 * @param totD an input-parameter of type String
	 * @param totE an input-parameter of type String
	 * @param totF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @param closureDate an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_64_0_0(String errCode, String lastFiscNum, String totA, String totB, String totC, String totD, String totE, String totF, String taxG, String taxH, String closureDate) {
		return Dispatch.callN(this, "CMD_64_0_0", new Object[] { errCode, lastFiscNum, totA, totB, totC, totD, totE, totF, taxG, taxH, closureDate}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errCode is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param lastFiscNum is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
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
	 * @param taxG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param closureDate is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_64_0_0(String[] errCode, String[] lastFiscNum, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] taxG, String[] taxH, String[] closureDate) {
		Variant vnt_errCode = new Variant();
		if( errCode == null || errCode.length == 0 )
			vnt_errCode.putNoParam();
		else
			vnt_errCode.putStringRef(errCode[0]);

		Variant vnt_lastFiscNum = new Variant();
		if( lastFiscNum == null || lastFiscNum.length == 0 )
			vnt_lastFiscNum.putNoParam();
		else
			vnt_lastFiscNum.putStringRef(lastFiscNum[0]);

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

		Variant vnt_closureDate = new Variant();
		if( closureDate == null || closureDate.length == 0 )
			vnt_closureDate.putNoParam();
		else
			vnt_closureDate.putStringRef(closureDate[0]);

		int result_of_CMD_64_0_0 = Dispatch.callN(this, "CMD_64_0_0", new Object[] { vnt_errCode, vnt_lastFiscNum, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_taxG, vnt_taxH, vnt_closureDate}).changeType(Variant.VariantInt).getInt();

		if( errCode != null && errCode.length > 0 )
			errCode[0] = vnt_errCode.toString();
		if( lastFiscNum != null && lastFiscNum.length > 0 )
			lastFiscNum[0] = vnt_lastFiscNum.toString();
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
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toString();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toString();
		if( closureDate != null && closureDate.length > 0 )
			closureDate[0] = vnt_closureDate.toString();

		return result_of_CMD_64_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param totA an input-parameter of type String
	 * @param totB an input-parameter of type String
	 * @param totC an input-parameter of type String
	 * @param totD an input-parameter of type String
	 * @param totE an input-parameter of type String
	 * @param totF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_65_0_0(String option, String totA, String totB, String totC, String totD, String totE, String totF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_65_0_0", new Object[] { option, totA, totB, totC, totD, totE, totF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
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
	 * @param taxG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_65_0_0(String option, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] taxG, String[] taxH) {
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

		int result_of_CMD_65_0_0 = Dispatch.callN(this, "CMD_65_0_0", new Object[] { option, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

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
		if( taxG != null && taxG.length > 0 )
			taxG[0] = vnt_taxG.toString();
		if( taxH != null && taxH.length > 0 )
			taxH[0] = vnt_taxH.toString();

		return result_of_CMD_65_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sTART_NUM an input-parameter of type String
	 * @param eND_NUM an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_66_0_0(String sTART_NUM, String eND_NUM) {
		return Dispatch.call(this, "CMD_66_0_0", sTART_NUM, eND_NUM).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param sTART_NUM an input-parameter of type String
	 * @param eND_NUM an input-parameter of type String
	 * @param cURRENT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_66_0_1(String sTART_NUM, String eND_NUM, String cURRENT) {
		return Dispatch.call(this, "CMD_66_0_1", sTART_NUM, eND_NUM, cURRENT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param sTART_NUM is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param eND_NUM is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param cURRENT is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_66_0_1(String[] sTART_NUM, String[] eND_NUM, String[] cURRENT) {
		Variant vnt_sTART_NUM = new Variant();
		if( sTART_NUM == null || sTART_NUM.length == 0 )
			vnt_sTART_NUM.putNoParam();
		else
			vnt_sTART_NUM.putStringRef(sTART_NUM[0]);

		Variant vnt_eND_NUM = new Variant();
		if( eND_NUM == null || eND_NUM.length == 0 )
			vnt_eND_NUM.putNoParam();
		else
			vnt_eND_NUM.putStringRef(eND_NUM[0]);

		Variant vnt_cURRENT = new Variant();
		if( cURRENT == null || cURRENT.length == 0 )
			vnt_cURRENT.putNoParam();
		else
			vnt_cURRENT.putStringRef(cURRENT[0]);

		int result_of_CMD_66_0_1 = Dispatch.call(this, "CMD_66_0_1", vnt_sTART_NUM, vnt_eND_NUM, vnt_cURRENT).changeType(Variant.VariantInt).getInt();

		if( sTART_NUM != null && sTART_NUM.length > 0 )
			sTART_NUM[0] = vnt_sTART_NUM.toString();
		if( eND_NUM != null && eND_NUM.length > 0 )
			eND_NUM[0] = vnt_eND_NUM.toString();
		if( cURRENT != null && cURRENT.length > 0 )
			cURRENT[0] = vnt_cURRENT.toString();

		return result_of_CMD_66_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param logical an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_68_0_0(String logical) {
		return Dispatch.call(this, "CMD_68_0_0", logical).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param logical is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_68_0_0(String[] logical) {
		Variant vnt_logical = new Variant();
		if( logical == null || logical.length == 0 )
			vnt_logical.putNoParam();
		else
			vnt_logical.putStringRef(logical[0]);

		int result_of_CMD_68_0_0 = Dispatch.call(this, "CMD_68_0_0", vnt_logical).changeType(Variant.VariantInt).getInt();

		if( logical != null && logical.length > 0 )
			logical[0] = vnt_logical.toString();

		return result_of_CMD_68_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param fM_Total an input-parameter of type String
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
	public int cMD_69_0_0(String option, String n, String closure, String fM_Total, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_69_0_0", new Object[] { option, n, closure, fM_Total, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_69_0_0(String option, String n, String[] closure, String[] fM_Total, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putStringRef(fM_Total[0]);

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

		int result_of_CMD_69_0_0 = Dispatch.callN(this, "CMD_69_0_0", new Object[] { option, n, vnt_closure, vnt_fM_Total, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toString();
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

		return result_of_CMD_69_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param amount an input-parameter of type String
	 * @param exitCode an input-parameter of type String
	 * @param cashSum an input-parameter of type String
	 * @param servIn an input-parameter of type String
	 * @param servOut an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_70_0_0(String amount, String exitCode, String cashSum, String servIn, String servOut) {
		return Dispatch.call(this, "CMD_70_0_0", amount, exitCode, cashSum, servIn, servOut).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param amount an input-parameter of type String
	 * @param exitCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param cashSum is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param servIn is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param servOut is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_70_0_0(String amount, String[] exitCode, String[] cashSum, String[] servIn, String[] servOut) {
		Variant vnt_exitCode = new Variant();
		if( exitCode == null || exitCode.length == 0 )
			vnt_exitCode.putNoParam();
		else
			vnt_exitCode.putStringRef(exitCode[0]);

		Variant vnt_cashSum = new Variant();
		if( cashSum == null || cashSum.length == 0 )
			vnt_cashSum.putNoParam();
		else
			vnt_cashSum.putStringRef(cashSum[0]);

		Variant vnt_servIn = new Variant();
		if( servIn == null || servIn.length == 0 )
			vnt_servIn.putNoParam();
		else
			vnt_servIn.putStringRef(servIn[0]);

		Variant vnt_servOut = new Variant();
		if( servOut == null || servOut.length == 0 )
			vnt_servOut.putNoParam();
		else
			vnt_servOut.putStringRef(servOut[0]);

		int result_of_CMD_70_0_0 = Dispatch.call(this, "CMD_70_0_0", amount, vnt_exitCode, vnt_cashSum, vnt_servIn, vnt_servOut).changeType(Variant.VariantInt).getInt();

		if( exitCode != null && exitCode.length > 0 )
			exitCode[0] = vnt_exitCode.toString();
		if( cashSum != null && cashSum.length > 0 )
			cashSum[0] = vnt_cashSum.toString();
		if( servIn != null && servIn.length > 0 )
			servIn[0] = vnt_servIn.toString();
		if( servOut != null && servOut.length > 0 )
			servOut[0] = vnt_servOut.toString();

		return result_of_CMD_70_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_71_0_0() {
		return Dispatch.call(this, "CMD_71_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startRec an input-parameter of type String
	 * @param endRec an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_73_0_0(String startRec, String endRec) {
		return Dispatch.call(this, "CMD_73_0_0", startRec, endRec).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param s0 an input-parameter of type String
	 * @param s1 an input-parameter of type String
	 * @param s2 an input-parameter of type String
	 * @param s3 an input-parameter of type String
	 * @param s4 an input-parameter of type String
	 * @param s5 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_74_0_0(String option, String s0, String s1, String s2, String s3, String s4, String s5) {
		return Dispatch.call(this, "CMD_74_0_0", option, s0, s1, s2, s3, s4, s5).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
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
	 * @return the result is of type int
	 */
	public int cMD_74_0_0(String option, String[] s0, String[] s1, String[] s2, String[] s3, String[] s4, String[] s5) {
		Variant vnt_s0 = new Variant();
		if( s0 == null || s0.length == 0 )
			vnt_s0.putNoParam();
		else
			vnt_s0.putStringRef(s0[0]);

		Variant vnt_s1 = new Variant();
		if( s1 == null || s1.length == 0 )
			vnt_s1.putNoParam();
		else
			vnt_s1.putStringRef(s1[0]);

		Variant vnt_s2 = new Variant();
		if( s2 == null || s2.length == 0 )
			vnt_s2.putNoParam();
		else
			vnt_s2.putStringRef(s2[0]);

		Variant vnt_s3 = new Variant();
		if( s3 == null || s3.length == 0 )
			vnt_s3.putNoParam();
		else
			vnt_s3.putStringRef(s3[0]);

		Variant vnt_s4 = new Variant();
		if( s4 == null || s4.length == 0 )
			vnt_s4.putNoParam();
		else
			vnt_s4.putStringRef(s4[0]);

		Variant vnt_s5 = new Variant();
		if( s5 == null || s5.length == 0 )
			vnt_s5.putNoParam();
		else
			vnt_s5.putStringRef(s5[0]);

		int result_of_CMD_74_0_0 = Dispatch.call(this, "CMD_74_0_0", option, vnt_s0, vnt_s1, vnt_s2, vnt_s3, vnt_s4, vnt_s5).changeType(Variant.VariantInt).getInt();

		if( s0 != null && s0.length > 0 )
			s0[0] = vnt_s0.toString();
		if( s1 != null && s1.length > 0 )
			s1[0] = vnt_s1.toString();
		if( s2 != null && s2.length > 0 )
			s2[0] = vnt_s2.toString();
		if( s3 != null && s3.length > 0 )
			s3[0] = vnt_s3.toString();
		if( s4 != null && s4.length > 0 )
			s4[0] = vnt_s4.toString();
		if( s5 != null && s5.length > 0 )
			s5[0] = vnt_s5.toString();

		return result_of_CMD_74_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param s0 an input-parameter of type String
	 * @param s1 an input-parameter of type String
	 * @param s2 an input-parameter of type String
	 * @param s3 an input-parameter of type String
	 * @param s4 an input-parameter of type String
	 * @param s5 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_74_0_1(String s0, String s1, String s2, String s3, String s4, String s5) {
		return Dispatch.call(this, "CMD_74_0_1", s0, s1, s2, s3, s4, s5).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
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
	 * @return the result is of type int
	 */
	public int cMD_74_0_1(String[] s0, String[] s1, String[] s2, String[] s3, String[] s4, String[] s5) {
		Variant vnt_s0 = new Variant();
		if( s0 == null || s0.length == 0 )
			vnt_s0.putNoParam();
		else
			vnt_s0.putStringRef(s0[0]);

		Variant vnt_s1 = new Variant();
		if( s1 == null || s1.length == 0 )
			vnt_s1.putNoParam();
		else
			vnt_s1.putStringRef(s1[0]);

		Variant vnt_s2 = new Variant();
		if( s2 == null || s2.length == 0 )
			vnt_s2.putNoParam();
		else
			vnt_s2.putStringRef(s2[0]);

		Variant vnt_s3 = new Variant();
		if( s3 == null || s3.length == 0 )
			vnt_s3.putNoParam();
		else
			vnt_s3.putStringRef(s3[0]);

		Variant vnt_s4 = new Variant();
		if( s4 == null || s4.length == 0 )
			vnt_s4.putNoParam();
		else
			vnt_s4.putStringRef(s4[0]);

		Variant vnt_s5 = new Variant();
		if( s5 == null || s5.length == 0 )
			vnt_s5.putNoParam();
		else
			vnt_s5.putStringRef(s5[0]);

		int result_of_CMD_74_0_1 = Dispatch.call(this, "CMD_74_0_1", vnt_s0, vnt_s1, vnt_s2, vnt_s3, vnt_s4, vnt_s5).changeType(Variant.VariantInt).getInt();

		if( s0 != null && s0.length > 0 )
			s0[0] = vnt_s0.toString();
		if( s1 != null && s1.length > 0 )
			s1[0] = vnt_s1.toString();
		if( s2 != null && s2.length > 0 )
			s2[0] = vnt_s2.toString();
		if( s3 != null && s3.length > 0 )
			s3[0] = vnt_s3.toString();
		if( s4 != null && s4.length > 0 )
			s4[0] = vnt_s4.toString();
		if( s5 != null && s5.length > 0 )
			s5[0] = vnt_s5.toString();

		return result_of_CMD_74_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bYTE_INDEX an input-parameter of type int
	 * @param bIT_INDEX an input-parameter of type int
	 * @return the result is of type String
	 */
	public String gET_STATUS_DESCRIPTION(int bYTE_INDEX, int bIT_INDEX) {
		return Dispatch.call(this, "GET_STATUS_DESCRIPTION", new Variant(bYTE_INDEX), new Variant(bIT_INDEX)).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bYTE_INDEX an input-parameter of type int
	 * @param bIT_INDEX an input-parameter of type int
	 * @return the result is of type boolean
	 */
	public boolean gET_BIT_STATE(int bYTE_INDEX, int bIT_INDEX) {
		return Dispatch.call(this, "GET_BIT_STATE", new Variant(bYTE_INDEX), new Variant(bIT_INDEX)).changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param fT_Opened an input-parameter of type String
	 * @param sales_Num an input-parameter of type String
	 * @param amount an input-parameter of type String
	 * @param tender an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_76_0_0(String fT_Opened, String sales_Num, String amount, String tender) {
		return Dispatch.call(this, "CMD_76_0_0", fT_Opened, sales_Num, amount, tender).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param fT_Opened is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param sales_Num is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param amount is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param tender is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_76_0_0(String[] fT_Opened, String[] sales_Num, String[] amount, String[] tender) {
		Variant vnt_fT_Opened = new Variant();
		if( fT_Opened == null || fT_Opened.length == 0 )
			vnt_fT_Opened.putNoParam();
		else
			vnt_fT_Opened.putStringRef(fT_Opened[0]);

		Variant vnt_sales_Num = new Variant();
		if( sales_Num == null || sales_Num.length == 0 )
			vnt_sales_Num.putNoParam();
		else
			vnt_sales_Num.putStringRef(sales_Num[0]);

		Variant vnt_amount = new Variant();
		if( amount == null || amount.length == 0 )
			vnt_amount.putNoParam();
		else
			vnt_amount.putStringRef(amount[0]);

		Variant vnt_tender = new Variant();
		if( tender == null || tender.length == 0 )
			vnt_tender.putNoParam();
		else
			vnt_tender.putStringRef(tender[0]);

		int result_of_CMD_76_0_0 = Dispatch.call(this, "CMD_76_0_0", vnt_fT_Opened, vnt_sales_Num, vnt_amount, vnt_tender).changeType(Variant.VariantInt).getInt();

		if( fT_Opened != null && fT_Opened.length > 0 )
			fT_Opened[0] = vnt_fT_Opened.toString();
		if( sales_Num != null && sales_Num.length > 0 )
			sales_Num[0] = vnt_sales_Num.toString();
		if( amount != null && amount.length > 0 )
			amount[0] = vnt_amount.toString();
		if( tender != null && tender.length > 0 )
			tender[0] = vnt_tender.toString();

		return result_of_CMD_76_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_79_0_0(String startDate, String endDate) {
		return Dispatch.call(this, "CMD_79_0_0", startDate, endDate).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param soundData an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_80_0_0(String soundData) {
		return Dispatch.call(this, "CMD_80_0_0", soundData).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param iNPUT_Multiplier an input-parameter of type String
	 * @param iNPUT_Decimals an input-parameter of type String
	 * @param iNPUT_Currency an input-parameter of type String
	 * @param iNPUT_EnabledT an input-parameter of type String
	 * @param iNPUT_TaxA an input-parameter of type String
	 * @param iNPUT_TaxB an input-parameter of type String
	 * @param iNPUT_TaxC an input-parameter of type String
	 * @param iNPUT_TaxD an input-parameter of type String
	 * @param iNPUT_TaxE an input-parameter of type String
	 * @param iNPUT_TaxF an input-parameter of type String
	 * @param iNPUT_TaxG an input-parameter of type String
	 * @param iNPUT_TaxH an input-parameter of type String
	 * @param oUTPUT_Multiplier an input-parameter of type String
	 * @param oUTPUT_Decimals an input-parameter of type String
	 * @param oUTPUT_Currency an input-parameter of type String
	 * @param oUTPUT_EnabledT an input-parameter of type String
	 * @param oUTPUT_TaxA an input-parameter of type String
	 * @param oUTPUT_TaxB an input-parameter of type String
	 * @param oUTPUT_TaxC an input-parameter of type String
	 * @param oUTPUT_TaxD an input-parameter of type String
	 * @param oUTPUT_TaxE an input-parameter of type String
	 * @param oUTPUT_TaxF an input-parameter of type String
	 * @param oUTPUT_TaxG an input-parameter of type String
	 * @param oUTPUT_TaxH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_83_0_0(String iNPUT_Multiplier, String iNPUT_Decimals, String iNPUT_Currency, String iNPUT_EnabledT, String iNPUT_TaxA, String iNPUT_TaxB, String iNPUT_TaxC, String iNPUT_TaxD, String iNPUT_TaxE, String iNPUT_TaxF, String iNPUT_TaxG, String iNPUT_TaxH, String oUTPUT_Multiplier, String oUTPUT_Decimals, String oUTPUT_Currency, String oUTPUT_EnabledT, String oUTPUT_TaxA, String oUTPUT_TaxB, String oUTPUT_TaxC, String oUTPUT_TaxD, String oUTPUT_TaxE, String oUTPUT_TaxF, String oUTPUT_TaxG, String oUTPUT_TaxH) {
		return Dispatch.callN(this, "CMD_83_0_0", new Object[] { iNPUT_Multiplier, iNPUT_Decimals, iNPUT_Currency, iNPUT_EnabledT, iNPUT_TaxA, iNPUT_TaxB, iNPUT_TaxC, iNPUT_TaxD, iNPUT_TaxE, iNPUT_TaxF, iNPUT_TaxG, iNPUT_TaxH, oUTPUT_Multiplier, oUTPUT_Decimals, oUTPUT_Currency, oUTPUT_EnabledT, oUTPUT_TaxA, oUTPUT_TaxB, oUTPUT_TaxC, oUTPUT_TaxD, oUTPUT_TaxE, oUTPUT_TaxF, oUTPUT_TaxG, oUTPUT_TaxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param iNPUT_Multiplier an input-parameter of type String
	 * @param iNPUT_Decimals an input-parameter of type String
	 * @param iNPUT_Currency an input-parameter of type String
	 * @param iNPUT_EnabledT an input-parameter of type String
	 * @param iNPUT_TaxA an input-parameter of type String
	 * @param iNPUT_TaxB an input-parameter of type String
	 * @param iNPUT_TaxC an input-parameter of type String
	 * @param iNPUT_TaxD an input-parameter of type String
	 * @param iNPUT_TaxE an input-parameter of type String
	 * @param iNPUT_TaxF an input-parameter of type String
	 * @param iNPUT_TaxG an input-parameter of type String
	 * @param iNPUT_TaxH an input-parameter of type String
	 * @param oUTPUT_Multiplier is an one-element array which sends the input-parameter
	 *                          to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_Decimals is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_Currency is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_EnabledT is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxA is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxB is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxC is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxD is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxE is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxF is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxG is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxH is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_83_0_0(String iNPUT_Multiplier, String iNPUT_Decimals, String iNPUT_Currency, String iNPUT_EnabledT, String iNPUT_TaxA, String iNPUT_TaxB, String iNPUT_TaxC, String iNPUT_TaxD, String iNPUT_TaxE, String iNPUT_TaxF, String iNPUT_TaxG, String iNPUT_TaxH, String[] oUTPUT_Multiplier, String[] oUTPUT_Decimals, String[] oUTPUT_Currency, String[] oUTPUT_EnabledT, String[] oUTPUT_TaxA, String[] oUTPUT_TaxB, String[] oUTPUT_TaxC, String[] oUTPUT_TaxD, String[] oUTPUT_TaxE, String[] oUTPUT_TaxF, String[] oUTPUT_TaxG, String[] oUTPUT_TaxH) {
		Variant vnt_oUTPUT_Multiplier = new Variant();
		if( oUTPUT_Multiplier == null || oUTPUT_Multiplier.length == 0 )
			vnt_oUTPUT_Multiplier.putNoParam();
		else
			vnt_oUTPUT_Multiplier.putStringRef(oUTPUT_Multiplier[0]);

		Variant vnt_oUTPUT_Decimals = new Variant();
		if( oUTPUT_Decimals == null || oUTPUT_Decimals.length == 0 )
			vnt_oUTPUT_Decimals.putNoParam();
		else
			vnt_oUTPUT_Decimals.putStringRef(oUTPUT_Decimals[0]);

		Variant vnt_oUTPUT_Currency = new Variant();
		if( oUTPUT_Currency == null || oUTPUT_Currency.length == 0 )
			vnt_oUTPUT_Currency.putNoParam();
		else
			vnt_oUTPUT_Currency.putStringRef(oUTPUT_Currency[0]);

		Variant vnt_oUTPUT_EnabledT = new Variant();
		if( oUTPUT_EnabledT == null || oUTPUT_EnabledT.length == 0 )
			vnt_oUTPUT_EnabledT.putNoParam();
		else
			vnt_oUTPUT_EnabledT.putStringRef(oUTPUT_EnabledT[0]);

		Variant vnt_oUTPUT_TaxA = new Variant();
		if( oUTPUT_TaxA == null || oUTPUT_TaxA.length == 0 )
			vnt_oUTPUT_TaxA.putNoParam();
		else
			vnt_oUTPUT_TaxA.putStringRef(oUTPUT_TaxA[0]);

		Variant vnt_oUTPUT_TaxB = new Variant();
		if( oUTPUT_TaxB == null || oUTPUT_TaxB.length == 0 )
			vnt_oUTPUT_TaxB.putNoParam();
		else
			vnt_oUTPUT_TaxB.putStringRef(oUTPUT_TaxB[0]);

		Variant vnt_oUTPUT_TaxC = new Variant();
		if( oUTPUT_TaxC == null || oUTPUT_TaxC.length == 0 )
			vnt_oUTPUT_TaxC.putNoParam();
		else
			vnt_oUTPUT_TaxC.putStringRef(oUTPUT_TaxC[0]);

		Variant vnt_oUTPUT_TaxD = new Variant();
		if( oUTPUT_TaxD == null || oUTPUT_TaxD.length == 0 )
			vnt_oUTPUT_TaxD.putNoParam();
		else
			vnt_oUTPUT_TaxD.putStringRef(oUTPUT_TaxD[0]);

		Variant vnt_oUTPUT_TaxE = new Variant();
		if( oUTPUT_TaxE == null || oUTPUT_TaxE.length == 0 )
			vnt_oUTPUT_TaxE.putNoParam();
		else
			vnt_oUTPUT_TaxE.putStringRef(oUTPUT_TaxE[0]);

		Variant vnt_oUTPUT_TaxF = new Variant();
		if( oUTPUT_TaxF == null || oUTPUT_TaxF.length == 0 )
			vnt_oUTPUT_TaxF.putNoParam();
		else
			vnt_oUTPUT_TaxF.putStringRef(oUTPUT_TaxF[0]);

		Variant vnt_oUTPUT_TaxG = new Variant();
		if( oUTPUT_TaxG == null || oUTPUT_TaxG.length == 0 )
			vnt_oUTPUT_TaxG.putNoParam();
		else
			vnt_oUTPUT_TaxG.putStringRef(oUTPUT_TaxG[0]);

		Variant vnt_oUTPUT_TaxH = new Variant();
		if( oUTPUT_TaxH == null || oUTPUT_TaxH.length == 0 )
			vnt_oUTPUT_TaxH.putNoParam();
		else
			vnt_oUTPUT_TaxH.putStringRef(oUTPUT_TaxH[0]);

		int result_of_CMD_83_0_0 = Dispatch.callN(this, "CMD_83_0_0", new Object[] { iNPUT_Multiplier, iNPUT_Decimals, iNPUT_Currency, iNPUT_EnabledT, iNPUT_TaxA, iNPUT_TaxB, iNPUT_TaxC, iNPUT_TaxD, iNPUT_TaxE, iNPUT_TaxF, iNPUT_TaxG, iNPUT_TaxH, vnt_oUTPUT_Multiplier, vnt_oUTPUT_Decimals, vnt_oUTPUT_Currency, vnt_oUTPUT_EnabledT, vnt_oUTPUT_TaxA, vnt_oUTPUT_TaxB, vnt_oUTPUT_TaxC, vnt_oUTPUT_TaxD, vnt_oUTPUT_TaxE, vnt_oUTPUT_TaxF, vnt_oUTPUT_TaxG, vnt_oUTPUT_TaxH}).changeType(Variant.VariantInt).getInt();

		if( oUTPUT_Multiplier != null && oUTPUT_Multiplier.length > 0 )
			oUTPUT_Multiplier[0] = vnt_oUTPUT_Multiplier.toString();
		if( oUTPUT_Decimals != null && oUTPUT_Decimals.length > 0 )
			oUTPUT_Decimals[0] = vnt_oUTPUT_Decimals.toString();
		if( oUTPUT_Currency != null && oUTPUT_Currency.length > 0 )
			oUTPUT_Currency[0] = vnt_oUTPUT_Currency.toString();
		if( oUTPUT_EnabledT != null && oUTPUT_EnabledT.length > 0 )
			oUTPUT_EnabledT[0] = vnt_oUTPUT_EnabledT.toString();
		if( oUTPUT_TaxA != null && oUTPUT_TaxA.length > 0 )
			oUTPUT_TaxA[0] = vnt_oUTPUT_TaxA.toString();
		if( oUTPUT_TaxB != null && oUTPUT_TaxB.length > 0 )
			oUTPUT_TaxB[0] = vnt_oUTPUT_TaxB.toString();
		if( oUTPUT_TaxC != null && oUTPUT_TaxC.length > 0 )
			oUTPUT_TaxC[0] = vnt_oUTPUT_TaxC.toString();
		if( oUTPUT_TaxD != null && oUTPUT_TaxD.length > 0 )
			oUTPUT_TaxD[0] = vnt_oUTPUT_TaxD.toString();
		if( oUTPUT_TaxE != null && oUTPUT_TaxE.length > 0 )
			oUTPUT_TaxE[0] = vnt_oUTPUT_TaxE.toString();
		if( oUTPUT_TaxF != null && oUTPUT_TaxF.length > 0 )
			oUTPUT_TaxF[0] = vnt_oUTPUT_TaxF.toString();
		if( oUTPUT_TaxG != null && oUTPUT_TaxG.length > 0 )
			oUTPUT_TaxG[0] = vnt_oUTPUT_TaxG.toString();
		if( oUTPUT_TaxH != null && oUTPUT_TaxH.length > 0 )
			oUTPUT_TaxH[0] = vnt_oUTPUT_TaxH.toString();

		return result_of_CMD_83_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param oUTPUT_Multiplier an input-parameter of type String
	 * @param oUTPUT_Decimals an input-parameter of type String
	 * @param oUTPUT_Currency an input-parameter of type String
	 * @param oUTPUT_EnabledT an input-parameter of type String
	 * @param oUTPUT_TaxA an input-parameter of type String
	 * @param oUTPUT_TaxB an input-parameter of type String
	 * @param oUTPUT_TaxC an input-parameter of type String
	 * @param oUTPUT_TaxD an input-parameter of type String
	 * @param oUTPUT_TaxE an input-parameter of type String
	 * @param oUTPUT_TaxF an input-parameter of type String
	 * @param oUTPUT_TaxG an input-parameter of type String
	 * @param oUTPUT_TaxH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_83_0_1(String oUTPUT_Multiplier, String oUTPUT_Decimals, String oUTPUT_Currency, String oUTPUT_EnabledT, String oUTPUT_TaxA, String oUTPUT_TaxB, String oUTPUT_TaxC, String oUTPUT_TaxD, String oUTPUT_TaxE, String oUTPUT_TaxF, String oUTPUT_TaxG, String oUTPUT_TaxH) {
		return Dispatch.callN(this, "CMD_83_0_1", new Object[] { oUTPUT_Multiplier, oUTPUT_Decimals, oUTPUT_Currency, oUTPUT_EnabledT, oUTPUT_TaxA, oUTPUT_TaxB, oUTPUT_TaxC, oUTPUT_TaxD, oUTPUT_TaxE, oUTPUT_TaxF, oUTPUT_TaxG, oUTPUT_TaxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param oUTPUT_Multiplier is an one-element array which sends the input-parameter
	 *                          to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_Decimals is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_Currency is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_EnabledT is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxA is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxB is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxC is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxD is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxE is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxF is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxG is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param oUTPUT_TaxH is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_83_0_1(String[] oUTPUT_Multiplier, String[] oUTPUT_Decimals, String[] oUTPUT_Currency, String[] oUTPUT_EnabledT, String[] oUTPUT_TaxA, String[] oUTPUT_TaxB, String[] oUTPUT_TaxC, String[] oUTPUT_TaxD, String[] oUTPUT_TaxE, String[] oUTPUT_TaxF, String[] oUTPUT_TaxG, String[] oUTPUT_TaxH) {
		Variant vnt_oUTPUT_Multiplier = new Variant();
		if( oUTPUT_Multiplier == null || oUTPUT_Multiplier.length == 0 )
			vnt_oUTPUT_Multiplier.putNoParam();
		else
			vnt_oUTPUT_Multiplier.putStringRef(oUTPUT_Multiplier[0]);

		Variant vnt_oUTPUT_Decimals = new Variant();
		if( oUTPUT_Decimals == null || oUTPUT_Decimals.length == 0 )
			vnt_oUTPUT_Decimals.putNoParam();
		else
			vnt_oUTPUT_Decimals.putStringRef(oUTPUT_Decimals[0]);

		Variant vnt_oUTPUT_Currency = new Variant();
		if( oUTPUT_Currency == null || oUTPUT_Currency.length == 0 )
			vnt_oUTPUT_Currency.putNoParam();
		else
			vnt_oUTPUT_Currency.putStringRef(oUTPUT_Currency[0]);

		Variant vnt_oUTPUT_EnabledT = new Variant();
		if( oUTPUT_EnabledT == null || oUTPUT_EnabledT.length == 0 )
			vnt_oUTPUT_EnabledT.putNoParam();
		else
			vnt_oUTPUT_EnabledT.putStringRef(oUTPUT_EnabledT[0]);

		Variant vnt_oUTPUT_TaxA = new Variant();
		if( oUTPUT_TaxA == null || oUTPUT_TaxA.length == 0 )
			vnt_oUTPUT_TaxA.putNoParam();
		else
			vnt_oUTPUT_TaxA.putStringRef(oUTPUT_TaxA[0]);

		Variant vnt_oUTPUT_TaxB = new Variant();
		if( oUTPUT_TaxB == null || oUTPUT_TaxB.length == 0 )
			vnt_oUTPUT_TaxB.putNoParam();
		else
			vnt_oUTPUT_TaxB.putStringRef(oUTPUT_TaxB[0]);

		Variant vnt_oUTPUT_TaxC = new Variant();
		if( oUTPUT_TaxC == null || oUTPUT_TaxC.length == 0 )
			vnt_oUTPUT_TaxC.putNoParam();
		else
			vnt_oUTPUT_TaxC.putStringRef(oUTPUT_TaxC[0]);

		Variant vnt_oUTPUT_TaxD = new Variant();
		if( oUTPUT_TaxD == null || oUTPUT_TaxD.length == 0 )
			vnt_oUTPUT_TaxD.putNoParam();
		else
			vnt_oUTPUT_TaxD.putStringRef(oUTPUT_TaxD[0]);

		Variant vnt_oUTPUT_TaxE = new Variant();
		if( oUTPUT_TaxE == null || oUTPUT_TaxE.length == 0 )
			vnt_oUTPUT_TaxE.putNoParam();
		else
			vnt_oUTPUT_TaxE.putStringRef(oUTPUT_TaxE[0]);

		Variant vnt_oUTPUT_TaxF = new Variant();
		if( oUTPUT_TaxF == null || oUTPUT_TaxF.length == 0 )
			vnt_oUTPUT_TaxF.putNoParam();
		else
			vnt_oUTPUT_TaxF.putStringRef(oUTPUT_TaxF[0]);

		Variant vnt_oUTPUT_TaxG = new Variant();
		if( oUTPUT_TaxG == null || oUTPUT_TaxG.length == 0 )
			vnt_oUTPUT_TaxG.putNoParam();
		else
			vnt_oUTPUT_TaxG.putStringRef(oUTPUT_TaxG[0]);

		Variant vnt_oUTPUT_TaxH = new Variant();
		if( oUTPUT_TaxH == null || oUTPUT_TaxH.length == 0 )
			vnt_oUTPUT_TaxH.putNoParam();
		else
			vnt_oUTPUT_TaxH.putStringRef(oUTPUT_TaxH[0]);

		int result_of_CMD_83_0_1 = Dispatch.callN(this, "CMD_83_0_1", new Object[] { vnt_oUTPUT_Multiplier, vnt_oUTPUT_Decimals, vnt_oUTPUT_Currency, vnt_oUTPUT_EnabledT, vnt_oUTPUT_TaxA, vnt_oUTPUT_TaxB, vnt_oUTPUT_TaxC, vnt_oUTPUT_TaxD, vnt_oUTPUT_TaxE, vnt_oUTPUT_TaxF, vnt_oUTPUT_TaxG, vnt_oUTPUT_TaxH}).changeType(Variant.VariantInt).getInt();

		if( oUTPUT_Multiplier != null && oUTPUT_Multiplier.length > 0 )
			oUTPUT_Multiplier[0] = vnt_oUTPUT_Multiplier.toString();
		if( oUTPUT_Decimals != null && oUTPUT_Decimals.length > 0 )
			oUTPUT_Decimals[0] = vnt_oUTPUT_Decimals.toString();
		if( oUTPUT_Currency != null && oUTPUT_Currency.length > 0 )
			oUTPUT_Currency[0] = vnt_oUTPUT_Currency.toString();
		if( oUTPUT_EnabledT != null && oUTPUT_EnabledT.length > 0 )
			oUTPUT_EnabledT[0] = vnt_oUTPUT_EnabledT.toString();
		if( oUTPUT_TaxA != null && oUTPUT_TaxA.length > 0 )
			oUTPUT_TaxA[0] = vnt_oUTPUT_TaxA.toString();
		if( oUTPUT_TaxB != null && oUTPUT_TaxB.length > 0 )
			oUTPUT_TaxB[0] = vnt_oUTPUT_TaxB.toString();
		if( oUTPUT_TaxC != null && oUTPUT_TaxC.length > 0 )
			oUTPUT_TaxC[0] = vnt_oUTPUT_TaxC.toString();
		if( oUTPUT_TaxD != null && oUTPUT_TaxD.length > 0 )
			oUTPUT_TaxD[0] = vnt_oUTPUT_TaxD.toString();
		if( oUTPUT_TaxE != null && oUTPUT_TaxE.length > 0 )
			oUTPUT_TaxE[0] = vnt_oUTPUT_TaxE.toString();
		if( oUTPUT_TaxF != null && oUTPUT_TaxF.length > 0 )
			oUTPUT_TaxF[0] = vnt_oUTPUT_TaxF.toString();
		if( oUTPUT_TaxG != null && oUTPUT_TaxG.length > 0 )
			oUTPUT_TaxG[0] = vnt_oUTPUT_TaxG.toString();
		if( oUTPUT_TaxH != null && oUTPUT_TaxH.length > 0 )
			oUTPUT_TaxH[0] = vnt_oUTPUT_TaxH.toString();

		return result_of_CMD_83_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bC_Type an input-parameter of type String
	 * @param bC_Data an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_84_0_0(String bC_Type, String bC_Data) {
		return Dispatch.call(this, "CMD_84_0_0", bC_Type, bC_Data).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bC_Type an input-parameter of type String
	 * @param bC_Data an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_84_0_1(String bC_Type, String bC_Data) {
		return Dispatch.call(this, "CMD_84_0_1", bC_Type, bC_Data).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param iNPUT_NAME an input-parameter of type String
	 * @param fP_Result an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_85_0_0(String option, String iNPUT_NAME, String fP_Result) {
		return Dispatch.call(this, "CMD_85_0_0", option, iNPUT_NAME, fP_Result).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param iNPUT_NAME an input-parameter of type String
	 * @param fP_Result is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_85_0_0(String option, String iNPUT_NAME, String[] fP_Result) {
		Variant vnt_fP_Result = new Variant();
		if( fP_Result == null || fP_Result.length == 0 )
			vnt_fP_Result.putNoParam();
		else
			vnt_fP_Result.putStringRef(fP_Result[0]);

		int result_of_CMD_85_0_0 = Dispatch.call(this, "CMD_85_0_0", option, iNPUT_NAME, vnt_fP_Result).changeType(Variant.VariantInt).getInt();

		if( fP_Result != null && fP_Result.length > 0 )
			fP_Result[0] = vnt_fP_Result.toString();

		return result_of_CMD_85_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param oUTPUT_NAME an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_85_0_1(String option, String oUTPUT_NAME) {
		return Dispatch.call(this, "CMD_85_0_1", option, oUTPUT_NAME).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param oUTPUT_NAME is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_85_0_1(String option, String[] oUTPUT_NAME) {
		Variant vnt_oUTPUT_NAME = new Variant();
		if( oUTPUT_NAME == null || oUTPUT_NAME.length == 0 )
			vnt_oUTPUT_NAME.putNoParam();
		else
			vnt_oUTPUT_NAME.putStringRef(oUTPUT_NAME[0]);

		int result_of_CMD_85_0_1 = Dispatch.call(this, "CMD_85_0_1", option, vnt_oUTPUT_NAME).changeType(Variant.VariantInt).getInt();

		if( oUTPUT_NAME != null && oUTPUT_NAME.length > 0 )
			oUTPUT_NAME[0] = vnt_oUTPUT_NAME.toString();

		return result_of_CMD_85_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lFMR_DT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_86_0_0(String lFMR_DT) {
		return Dispatch.call(this, "CMD_86_0_0", lFMR_DT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param lFMR_DT is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_86_0_0(String[] lFMR_DT) {
		Variant vnt_lFMR_DT = new Variant();
		if( lFMR_DT == null || lFMR_DT.length == 0 )
			vnt_lFMR_DT.putNoParam();
		else
			vnt_lFMR_DT.putStringRef(lFMR_DT[0]);

		int result_of_CMD_86_0_0 = Dispatch.call(this, "CMD_86_0_0", vnt_lFMR_DT).changeType(Variant.VariantInt).getInt();

		if( lFMR_DT != null && lFMR_DT.length > 0 )
			lFMR_DT[0] = vnt_lFMR_DT.toString();

		return result_of_CMD_86_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dD an input-parameter of type String
	 * @param mM an input-parameter of type String
	 * @param yYYY an input-parameter of type String
	 * @param hH an input-parameter of type String
	 * @param mMM an input-parameter of type String
	 * @param sS an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_86_0_1(String dD, String mM, String yYYY, String hH, String mMM, String sS) {
		return Dispatch.call(this, "CMD_86_0_1", dD, mM, yYYY, hH, mMM, sS).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dD is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param mM is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param yYYY is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param hH is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param mMM is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param sS is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_86_0_1(String[] dD, String[] mM, String[] yYYY, String[] hH, String[] mMM, String[] sS) {
		Variant vnt_dD = new Variant();
		if( dD == null || dD.length == 0 )
			vnt_dD.putNoParam();
		else
			vnt_dD.putStringRef(dD[0]);

		Variant vnt_mM = new Variant();
		if( mM == null || mM.length == 0 )
			vnt_mM.putNoParam();
		else
			vnt_mM.putStringRef(mM[0]);

		Variant vnt_yYYY = new Variant();
		if( yYYY == null || yYYY.length == 0 )
			vnt_yYYY.putNoParam();
		else
			vnt_yYYY.putStringRef(yYYY[0]);

		Variant vnt_hH = new Variant();
		if( hH == null || hH.length == 0 )
			vnt_hH.putNoParam();
		else
			vnt_hH.putStringRef(hH[0]);

		Variant vnt_mMM = new Variant();
		if( mMM == null || mMM.length == 0 )
			vnt_mMM.putNoParam();
		else
			vnt_mMM.putStringRef(mMM[0]);

		Variant vnt_sS = new Variant();
		if( sS == null || sS.length == 0 )
			vnt_sS.putNoParam();
		else
			vnt_sS.putStringRef(sS[0]);

		int result_of_CMD_86_0_1 = Dispatch.call(this, "CMD_86_0_1", vnt_dD, vnt_mM, vnt_yYYY, vnt_hH, vnt_mMM, vnt_sS).changeType(Variant.VariantInt).getInt();

		if( dD != null && dD.length > 0 )
			dD[0] = vnt_dD.toString();
		if( mM != null && mM.length > 0 )
			mM[0] = vnt_mM.toString();
		if( yYYY != null && yYYY.length > 0 )
			yYYY[0] = vnt_yYYY.toString();
		if( hH != null && hH.length > 0 )
			hH[0] = vnt_hH.toString();
		if( mMM != null && mMM.length > 0 )
			mMM[0] = vnt_mMM.toString();
		if( sS != null && sS.length > 0 )
			sS[0] = vnt_sS.toString();

		return result_of_CMD_86_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_87_0_0(String dept, String taxGr, String line1, String line2) {
		return Dispatch.call(this, "CMD_87_0_0", dept, taxGr, line1, line2).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param line1 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_87_0_1(String dept, String taxGr, String line1) {
		return Dispatch.call(this, "CMD_87_0_1", dept, taxGr, line1).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param exitCode an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param recSales an input-parameter of type String
	 * @param recSum an input-parameter of type String
	 * @param totSales an input-parameter of type String
	 * @param totSum an input-parameter of type String
	 * @param line1 an input-parameter of type String
	 * @param line2 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_88_0_0(String dept, String exitCode, String taxGr, String recSales, String recSum, String totSales, String totSum, String line1, String line2) {
		return Dispatch.callN(this, "CMD_88_0_0", new Object[] { dept, exitCode, taxGr, recSales, recSum, totSales, totSum, line1, line2}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param exitCode is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param recSales is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	 * @return the result is of type int
	 */
	public int cMD_88_0_0(String dept, String[] exitCode, String[] taxGr, String[] recSales, String[] recSum, String[] totSales, String[] totSum, String[] line1, String[] line2) {
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

		Variant vnt_recSales = new Variant();
		if( recSales == null || recSales.length == 0 )
			vnt_recSales.putNoParam();
		else
			vnt_recSales.putStringRef(recSales[0]);

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

		int result_of_CMD_88_0_0 = Dispatch.callN(this, "CMD_88_0_0", new Object[] { dept, vnt_exitCode, vnt_taxGr, vnt_recSales, vnt_recSum, vnt_totSales, vnt_totSum, vnt_line1, vnt_line2}).changeType(Variant.VariantInt).getInt();

		if( exitCode != null && exitCode.length > 0 )
			exitCode[0] = vnt_exitCode.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( recSales != null && recSales.length > 0 )
			recSales[0] = vnt_recSales.toString();
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

		return result_of_CMD_88_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param printerName an input-parameter of type String
	 * @param fwRev an input-parameter of type String
	 * @param fwDate an input-parameter of type String
	 * @param fwTime an input-parameter of type String
	 * @param chk an input-parameter of type String
	 * @param sw an input-parameter of type String
	 * @param serial an input-parameter of type String
	 * @param fM an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_90_0_0(String printerName, String fwRev, String fwDate, String fwTime, String chk, String sw, String serial, String fM) {
		return Dispatch.call(this, "CMD_90_0_0", printerName, fwRev, fwDate, fwTime, chk, sw, serial, fM).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param printerName is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param fwRev is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param fwDate is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fwTime is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param chk is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @param sw is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param serial is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param fM is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_90_0_0(String[] printerName, String[] fwRev, String[] fwDate, String[] fwTime, String[] chk, String[] sw, String[] serial, String[] fM) {
		Variant vnt_printerName = new Variant();
		if( printerName == null || printerName.length == 0 )
			vnt_printerName.putNoParam();
		else
			vnt_printerName.putStringRef(printerName[0]);

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

		Variant vnt_sw = new Variant();
		if( sw == null || sw.length == 0 )
			vnt_sw.putNoParam();
		else
			vnt_sw.putStringRef(sw[0]);

		Variant vnt_serial = new Variant();
		if( serial == null || serial.length == 0 )
			vnt_serial.putNoParam();
		else
			vnt_serial.putStringRef(serial[0]);

		Variant vnt_fM = new Variant();
		if( fM == null || fM.length == 0 )
			vnt_fM.putNoParam();
		else
			vnt_fM.putStringRef(fM[0]);

		int result_of_CMD_90_0_0 = Dispatch.call(this, "CMD_90_0_0", vnt_printerName, vnt_fwRev, vnt_fwDate, vnt_fwTime, vnt_chk, vnt_sw, vnt_serial, vnt_fM).changeType(Variant.VariantInt).getInt();

		if( printerName != null && printerName.length > 0 )
			printerName[0] = vnt_printerName.toString();
		if( fwRev != null && fwRev.length > 0 )
			fwRev[0] = vnt_fwRev.toString();
		if( fwDate != null && fwDate.length > 0 )
			fwDate[0] = vnt_fwDate.toString();
		if( fwTime != null && fwTime.length > 0 )
			fwTime[0] = vnt_fwTime.toString();
		if( chk != null && chk.length > 0 )
			chk[0] = vnt_chk.toString();
		if( sw != null && sw.length > 0 )
			sw[0] = vnt_sw.toString();
		if( serial != null && serial.length > 0 )
			serial[0] = vnt_serial.toString();
		if( fM != null && fM.length > 0 )
			fM[0] = vnt_fM.toString();

		return result_of_CMD_90_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lineType an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_92_0_0(String lineType) {
		return Dispatch.call(this, "CMD_92_0_0", lineType).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startDate an input-parameter of type String
	 * @param endDate an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_94_0_0(String startDate, String endDate) {
		return Dispatch.call(this, "CMD_94_0_0", startDate, endDate).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param startNumber an input-parameter of type String
	 * @param endNumber an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_95_0_0(String startNumber, String endNumber) {
		return Dispatch.call(this, "CMD_95_0_0", startNumber, endNumber).changeType(Variant.VariantInt).getInt();
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
	 * @return the result is of type int
	 */
	public int cMD_97_0_0(String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.call(this, "CMD_97_0_0", taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH).changeType(Variant.VariantInt).getInt();
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
	 * @return the result is of type int
	 */
	public int cMD_97_0_0(String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
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

		int result_of_CMD_97_0_0 = Dispatch.call(this, "CMD_97_0_0", vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH).changeType(Variant.VariantInt).getInt();

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

		return result_of_CMD_97_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param eIK_Text an input-parameter of type String
	 * @param eIK_Name an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_99_0_0(String eIK_Text, String eIK_Name) {
		return Dispatch.call(this, "CMD_99_0_0", eIK_Text, eIK_Name).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param eIK_Text is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param eIK_Name is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_99_0_0(String[] eIK_Text, String[] eIK_Name) {
		Variant vnt_eIK_Text = new Variant();
		if( eIK_Text == null || eIK_Text.length == 0 )
			vnt_eIK_Text.putNoParam();
		else
			vnt_eIK_Text.putStringRef(eIK_Text[0]);

		Variant vnt_eIK_Name = new Variant();
		if( eIK_Name == null || eIK_Name.length == 0 )
			vnt_eIK_Name.putNoParam();
		else
			vnt_eIK_Name.putStringRef(eIK_Name[0]);

		int result_of_CMD_99_0_0 = Dispatch.call(this, "CMD_99_0_0", vnt_eIK_Text, vnt_eIK_Name).changeType(Variant.VariantInt).getInt();

		if( eIK_Text != null && eIK_Text.length > 0 )
			eIK_Text[0] = vnt_eIK_Text.toString();
		if( eIK_Name != null && eIK_Name.length > 0 )
			eIK_Name[0] = vnt_eIK_Name.toString();

		return result_of_CMD_99_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param input_Text an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_100_0_0(String input_Text) {
		return Dispatch.call(this, "CMD_100_0_0", input_Text).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param oldPwd an input-parameter of type String
	 * @param newPwd an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_101_0_0(String opCode, String oldPwd, String newPwd) {
		return Dispatch.call(this, "CMD_101_0_0", opCode, oldPwd, newPwd).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param opCode an input-parameter of type String
	 * @param pwd an input-parameter of type String
	 * @param opName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_102_0_0(String opCode, String pwd, String opName) {
		return Dispatch.call(this, "CMD_102_0_0", opCode, pwd, opName).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param canVd an input-parameter of type String
	 * @param taxA an input-parameter of type String
	 * @param taxB an input-parameter of type String
	 * @param taxC an input-parameter of type String
	 * @param taxD an input-parameter of type String
	 * @param taxE an input-parameter of type String
	 * @param taxF an input-parameter of type String
	 * @param taxG an input-parameter of type String
	 * @param taxH an input-parameter of type String
	 * @param inv an input-parameter of type String
	 * @param invNmb an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_103_0_0(String canVd, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH, String inv, String invNmb) {
		return Dispatch.callN(this, "CMD_103_0_0", new Object[] { canVd, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH, inv, invNmb}).changeType(Variant.VariantInt).getInt();
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
	 * @param taxF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param taxH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param inv an input-parameter of type String
	 * @param invNmb is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_103_0_0(String[] canVd, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH, String inv, String[] invNmb) {
		Variant vnt_canVd = new Variant();
		if( canVd == null || canVd.length == 0 )
			vnt_canVd.putNoParam();
		else
			vnt_canVd.putStringRef(canVd[0]);

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

		Variant vnt_invNmb = new Variant();
		if( invNmb == null || invNmb.length == 0 )
			vnt_invNmb.putNoParam();
		else
			vnt_invNmb.putStringRef(invNmb[0]);

		int result_of_CMD_103_0_0 = Dispatch.callN(this, "CMD_103_0_0", new Object[] { vnt_canVd, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH, inv, vnt_invNmb}).changeType(Variant.VariantInt).getInt();

		if( canVd != null && canVd.length > 0 )
			canVd[0] = vnt_canVd.toString();
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
		if( invNmb != null && invNmb.length > 0 )
			invNmb[0] = vnt_invNmb.toString();

		return result_of_CMD_103_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_105_0_0() {
		return Dispatch.call(this, "CMD_105_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param mSec an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_106_0_0(String mSec) {
		return Dispatch.call(this, "CMD_106_0_0", mSec).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param progr an input-parameter of type String
	 * @param len an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_0_0(String errorCode, String total, String progr, String len) {
		return Dispatch.call(this, "CMD_107_0_0", errorCode, total, progr, len).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param progr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param len is an one-element array which sends the input-parameter
	 *            to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_0_0(String[] errorCode, String[] total, String[] progr, String[] len) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_progr = new Variant();
		if( progr == null || progr.length == 0 )
			vnt_progr.putNoParam();
		else
			vnt_progr.putStringRef(progr[0]);

		Variant vnt_len = new Variant();
		if( len == null || len.length == 0 )
			vnt_len.putNoParam();
		else
			vnt_len.putStringRef(len[0]);

		int result_of_CMD_107_0_0 = Dispatch.call(this, "CMD_107_0_0", vnt_errorCode, vnt_total, vnt_progr, vnt_len).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( progr != null && progr.length > 0 )
			progr[0] = vnt_progr.toString();
		if( len != null && len.length > 0 )
			len[0] = vnt_len.toString();

		return result_of_CMD_107_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxGr an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param replace an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_1_0(String taxGr, String pLU, String group, String sPrice, String replace, String quantity, String itemName, String errorCode) {
		return Dispatch.call(this, "CMD_107_1_0", taxGr, pLU, group, sPrice, replace, quantity, itemName, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param taxGr an input-parameter of type String
	 * @param pLU an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param replace an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_1_0(String taxGr, String pLU, String group, String sPrice, String replace, String quantity, String itemName, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_107_1_0 = Dispatch.call(this, "CMD_107_1_0", taxGr, pLU, group, sPrice, replace, quantity, itemName, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_107_1_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_2_0(String pLU, String quantity, String errorCode) {
		return Dispatch.call(this, "CMD_107_2_0", pLU, quantity, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param quantity an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_2_0(String pLU, String quantity, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_107_2_0 = Dispatch.call(this, "CMD_107_2_0", pLU, quantity, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_107_2_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_4_0(String errorCode) {
		return Dispatch.call(this, "CMD_107_4_0", errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_4_0(String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_107_4_0 = Dispatch.call(this, "CMD_107_4_0", vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_107_4_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_4_1(String pLU, String errorCode) {
		return Dispatch.call(this, "CMD_107_4_1", pLU, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_4_1(String pLU, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_107_4_1 = Dispatch.call(this, "CMD_107_4_1", pLU, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_107_4_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU1 an input-parameter of type String
	 * @param pLU2 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_4_2(String pLU1, String pLU2, String errorCode) {
		return Dispatch.call(this, "CMD_107_4_2", pLU1, pLU2, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU1 an input-parameter of type String
	 * @param pLU2 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_4_2(String pLU1, String pLU2, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_107_4_2 = Dispatch.call(this, "CMD_107_4_2", pLU1, pLU2, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_107_4_2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_5_0(String pLU, String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_5_0", new Object[] { pLU, errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_5_0(String pLU, String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_5_0 = Dispatch.callN(this, "CMD_107_5_0", new Object[] { pLU, vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_5_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_6_0(String pLU, String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_6_0", new Object[] { pLU, errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_6_0(String pLU, String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_6_0 = Dispatch.callN(this, "CMD_107_6_0", new Object[] { pLU, vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_6_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_7_0(String pLU, String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_7_0", new Object[] { pLU, errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_7_0(String pLU, String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_7_0 = Dispatch.callN(this, "CMD_107_7_0", new Object[] { pLU, vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_7_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_8_0(String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_8_0", new Object[] { errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_8_0(String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_8_0 = Dispatch.callN(this, "CMD_107_8_0", new Object[] { vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_8_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_9_0(String pLU, String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_9_0", new Object[] { pLU, errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_9_0(String pLU, String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_9_0 = Dispatch.callN(this, "CMD_107_9_0", new Object[] { pLU, vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_9_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_10_0(String pLU, String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_10_0", new Object[] { pLU, errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_10_0(String pLU, String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_10_0 = Dispatch.callN(this, "CMD_107_10_0", new Object[] { pLU, vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_10_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @param taxGr an input-parameter of type String
	 * @param group an input-parameter of type String
	 * @param sPrice an input-parameter of type String
	 * @param total an input-parameter of type String
	 * @param sold an input-parameter of type String
	 * @param available an input-parameter of type String
	 * @param itemName an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_11_0(String errorCode, String aNSWER_PLU, String taxGr, String group, String sPrice, String total, String sold, String available, String itemName) {
		return Dispatch.callN(this, "CMD_107_11_0", new Object[] { errorCode, aNSWER_PLU, taxGr, group, sPrice, total, sold, available, itemName}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param taxGr is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param group is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sPrice is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param total is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param sold is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param available is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param itemName is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_11_0(String[] errorCode, String[] aNSWER_PLU, String[] taxGr, String[] group, String[] sPrice, String[] total, String[] sold, String[] available, String[] itemName) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		Variant vnt_taxGr = new Variant();
		if( taxGr == null || taxGr.length == 0 )
			vnt_taxGr.putNoParam();
		else
			vnt_taxGr.putStringRef(taxGr[0]);

		Variant vnt_group = new Variant();
		if( group == null || group.length == 0 )
			vnt_group.putNoParam();
		else
			vnt_group.putStringRef(group[0]);

		Variant vnt_sPrice = new Variant();
		if( sPrice == null || sPrice.length == 0 )
			vnt_sPrice.putNoParam();
		else
			vnt_sPrice.putStringRef(sPrice[0]);

		Variant vnt_total = new Variant();
		if( total == null || total.length == 0 )
			vnt_total.putNoParam();
		else
			vnt_total.putStringRef(total[0]);

		Variant vnt_sold = new Variant();
		if( sold == null || sold.length == 0 )
			vnt_sold.putNoParam();
		else
			vnt_sold.putStringRef(sold[0]);

		Variant vnt_available = new Variant();
		if( available == null || available.length == 0 )
			vnt_available.putNoParam();
		else
			vnt_available.putStringRef(available[0]);

		Variant vnt_itemName = new Variant();
		if( itemName == null || itemName.length == 0 )
			vnt_itemName.putNoParam();
		else
			vnt_itemName.putStringRef(itemName[0]);

		int result_of_CMD_107_11_0 = Dispatch.callN(this, "CMD_107_11_0", new Object[] { vnt_errorCode, vnt_aNSWER_PLU, vnt_taxGr, vnt_group, vnt_sPrice, vnt_total, vnt_sold, vnt_available, vnt_itemName}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();
		if( taxGr != null && taxGr.length > 0 )
			taxGr[0] = vnt_taxGr.toString();
		if( group != null && group.length > 0 )
			group[0] = vnt_group.toString();
		if( sPrice != null && sPrice.length > 0 )
			sPrice[0] = vnt_sPrice.toString();
		if( total != null && total.length > 0 )
			total[0] = vnt_total.toString();
		if( sold != null && sold.length > 0 )
			sold[0] = vnt_sold.toString();
		if( available != null && available.length > 0 )
			available[0] = vnt_available.toString();
		if( itemName != null && itemName.length > 0 )
			itemName[0] = vnt_itemName.toString();

		return result_of_CMD_107_11_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_12_0(String pLU, String errorCode, String aNSWER_PLU) {
		return Dispatch.call(this, "CMD_107_12_0", pLU, errorCode, aNSWER_PLU).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_12_0(String pLU, String[] errorCode, String[] aNSWER_PLU) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		int result_of_CMD_107_12_0 = Dispatch.call(this, "CMD_107_12_0", pLU, vnt_errorCode, vnt_aNSWER_PLU).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();

		return result_of_CMD_107_12_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param aNSWER_PLU an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_107_12_1(String pLU, String errorCode, String aNSWER_PLU) {
		return Dispatch.call(this, "CMD_107_12_1", pLU, errorCode, aNSWER_PLU).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param pLU an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param aNSWER_PLU is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_107_12_1(String pLU, String[] errorCode, String[] aNSWER_PLU) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_aNSWER_PLU = new Variant();
		if( aNSWER_PLU == null || aNSWER_PLU.length == 0 )
			vnt_aNSWER_PLU.putNoParam();
		else
			vnt_aNSWER_PLU.putStringRef(aNSWER_PLU[0]);

		int result_of_CMD_107_12_1 = Dispatch.call(this, "CMD_107_12_1", pLU, vnt_errorCode, vnt_aNSWER_PLU).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( aNSWER_PLU != null && aNSWER_PLU.length > 0 )
			aNSWER_PLU[0] = vnt_aNSWER_PLU.toString();

		return result_of_CMD_107_12_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param fM_Total an input-parameter of type String
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
	public int cMD_108_0_0(String option, String n, String closure, String fM_Total, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_108_0_0", new Object[] { option, n, closure, fM_Total, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_108_0_0(String option, String n, String[] closure, String[] fM_Total, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putStringRef(fM_Total[0]);

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

		int result_of_CMD_108_0_0 = Dispatch.callN(this, "CMD_108_0_0", new Object[] { option, n, vnt_closure, vnt_fM_Total, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toString();
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

		return result_of_CMD_108_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param ecrCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_109_0_0(String ecrCount) {
		return Dispatch.call(this, "CMD_109_0_0", ecrCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cash an input-parameter of type String
	 * @param credit an input-parameter of type String
	 * @param debit an input-parameter of type String
	 * @param cheque an input-parameter of type String
	 * @param pay1 an input-parameter of type String
	 * @param pay2 an input-parameter of type String
	 * @param pay3 an input-parameter of type String
	 * @param pay4 an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipt an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_110_0_0(String cash, String credit, String debit, String cheque, String pay1, String pay2, String pay3, String pay4, String closure, String receipt) {
		return Dispatch.callN(this, "CMD_110_0_0", new Object[] { cash, credit, debit, cheque, pay1, pay2, pay3, pay4, closure, receipt}).changeType(Variant.VariantInt).getInt();
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
	 * @param pay1 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay2 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay3 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param pay4 is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipt is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_110_0_0(String[] cash, String[] credit, String[] debit, String[] cheque, String[] pay1, String[] pay2, String[] pay3, String[] pay4, String[] closure, String[] receipt) {
		Variant vnt_cash = new Variant();
		if( cash == null || cash.length == 0 )
			vnt_cash.putNoParam();
		else
			vnt_cash.putStringRef(cash[0]);

		Variant vnt_credit = new Variant();
		if( credit == null || credit.length == 0 )
			vnt_credit.putNoParam();
		else
			vnt_credit.putStringRef(credit[0]);

		Variant vnt_debit = new Variant();
		if( debit == null || debit.length == 0 )
			vnt_debit.putNoParam();
		else
			vnt_debit.putStringRef(debit[0]);

		Variant vnt_cheque = new Variant();
		if( cheque == null || cheque.length == 0 )
			vnt_cheque.putNoParam();
		else
			vnt_cheque.putStringRef(cheque[0]);

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

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipt = new Variant();
		if( receipt == null || receipt.length == 0 )
			vnt_receipt.putNoParam();
		else
			vnt_receipt.putStringRef(receipt[0]);

		int result_of_CMD_110_0_0 = Dispatch.callN(this, "CMD_110_0_0", new Object[] { vnt_cash, vnt_credit, vnt_debit, vnt_cheque, vnt_pay1, vnt_pay2, vnt_pay3, vnt_pay4, vnt_closure, vnt_receipt}).changeType(Variant.VariantInt).getInt();

		if( cash != null && cash.length > 0 )
			cash[0] = vnt_cash.toString();
		if( credit != null && credit.length > 0 )
			credit[0] = vnt_credit.toString();
		if( debit != null && debit.length > 0 )
			debit[0] = vnt_debit.toString();
		if( cheque != null && cheque.length > 0 )
			cheque[0] = vnt_cheque.toString();
		if( pay1 != null && pay1.length > 0 )
			pay1[0] = vnt_pay1.toString();
		if( pay2 != null && pay2.length > 0 )
			pay2[0] = vnt_pay2.toString();
		if( pay3 != null && pay3.length > 0 )
			pay3[0] = vnt_pay3.toString();
		if( pay4 != null && pay4.length > 0 )
			pay4[0] = vnt_pay4.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipt != null && receipt.length > 0 )
			receipt[0] = vnt_receipt.toString();

		return result_of_CMD_110_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startNum an input-parameter of type String
	 * @param endNum an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_111_0_0(String option, String startNum, String endNum, String errorCode) {
		return Dispatch.call(this, "CMD_111_0_0", option, startNum, endNum, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startNum an input-parameter of type String
	 * @param endNum an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_111_0_0(String option, String startNum, String endNum, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_111_0_0 = Dispatch.call(this, "CMD_111_0_0", option, startNum, endNum, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_111_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startNum an input-parameter of type String
	 * @param endNum an input-parameter of type String
	 * @param groupNum an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_111_1_0(String option, String startNum, String endNum, String groupNum, String errorCode) {
		return Dispatch.call(this, "CMD_111_1_0", option, startNum, endNum, groupNum, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param startNum an input-parameter of type String
	 * @param endNum an input-parameter of type String
	 * @param groupNum an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_111_1_0(String option, String startNum, String endNum, String groupNum, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_111_1_0 = Dispatch.call(this, "CMD_111_1_0", option, startNum, endNum, groupNum, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_111_1_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_111_2_0(String option, String errorCode) {
		return Dispatch.call(this, "CMD_111_2_0", option, errorCode).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_111_2_0(String option, String[] errorCode) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		int result_of_CMD_111_2_0 = Dispatch.call(this, "CMD_111_2_0", option, vnt_errorCode).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();

		return result_of_CMD_111_2_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param operatorNumber an input-parameter of type String
	 * @param receiptsNumber an input-parameter of type String
	 * @param registeredSalesNumber an input-parameter of type String
	 * @param totalAccumulatedSum an input-parameter of type String
	 * @param discountsNumber an input-parameter of type String
	 * @param totalDiscounts an input-parameter of type String
	 * @param surchargeNumber an input-parameter of type String
	 * @param totalSurcharge an input-parameter of type String
	 * @param voidsNumber an input-parameter of type String
	 * @param voidsTotal an input-parameter of type String
	 * @param operatorName an input-parameter of type String
	 * @param operatorPassword an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_112_0_0(String operatorNumber, String receiptsNumber, String registeredSalesNumber, String totalAccumulatedSum, String discountsNumber, String totalDiscounts, String surchargeNumber, String totalSurcharge, String voidsNumber, String voidsTotal, String operatorName, String operatorPassword) {
		return Dispatch.callN(this, "CMD_112_0_0", new Object[] { operatorNumber, receiptsNumber, registeredSalesNumber, totalAccumulatedSum, discountsNumber, totalDiscounts, surchargeNumber, totalSurcharge, voidsNumber, voidsTotal, operatorName, operatorPassword}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param operatorNumber an input-parameter of type String
	 * @param receiptsNumber is an one-element array which sends the input-parameter
	 *                       to the ActiveX-Component and receives the output-parameter
	 * @param registeredSalesNumber is an one-element array which sends the input-parameter
	 *                              to the ActiveX-Component and receives the output-parameter
	 * @param totalAccumulatedSum is an one-element array which sends the input-parameter
	 *                            to the ActiveX-Component and receives the output-parameter
	 * @param discountsNumber is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param totalDiscounts is an one-element array which sends the input-parameter
	 *                       to the ActiveX-Component and receives the output-parameter
	 * @param surchargeNumber is an one-element array which sends the input-parameter
	 *                        to the ActiveX-Component and receives the output-parameter
	 * @param totalSurcharge is an one-element array which sends the input-parameter
	 *                       to the ActiveX-Component and receives the output-parameter
	 * @param voidsNumber is an one-element array which sends the input-parameter
	 *                    to the ActiveX-Component and receives the output-parameter
	 * @param voidsTotal is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param operatorName is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @param operatorPassword is an one-element array which sends the input-parameter
	 *                         to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_112_0_0(String operatorNumber, String[] receiptsNumber, String[] registeredSalesNumber, String[] totalAccumulatedSum, String[] discountsNumber, String[] totalDiscounts, String[] surchargeNumber, String[] totalSurcharge, String[] voidsNumber, String[] voidsTotal, String[] operatorName, String[] operatorPassword) {
		Variant vnt_receiptsNumber = new Variant();
		if( receiptsNumber == null || receiptsNumber.length == 0 )
			vnt_receiptsNumber.putNoParam();
		else
			vnt_receiptsNumber.putStringRef(receiptsNumber[0]);

		Variant vnt_registeredSalesNumber = new Variant();
		if( registeredSalesNumber == null || registeredSalesNumber.length == 0 )
			vnt_registeredSalesNumber.putNoParam();
		else
			vnt_registeredSalesNumber.putStringRef(registeredSalesNumber[0]);

		Variant vnt_totalAccumulatedSum = new Variant();
		if( totalAccumulatedSum == null || totalAccumulatedSum.length == 0 )
			vnt_totalAccumulatedSum.putNoParam();
		else
			vnt_totalAccumulatedSum.putStringRef(totalAccumulatedSum[0]);

		Variant vnt_discountsNumber = new Variant();
		if( discountsNumber == null || discountsNumber.length == 0 )
			vnt_discountsNumber.putNoParam();
		else
			vnt_discountsNumber.putStringRef(discountsNumber[0]);

		Variant vnt_totalDiscounts = new Variant();
		if( totalDiscounts == null || totalDiscounts.length == 0 )
			vnt_totalDiscounts.putNoParam();
		else
			vnt_totalDiscounts.putStringRef(totalDiscounts[0]);

		Variant vnt_surchargeNumber = new Variant();
		if( surchargeNumber == null || surchargeNumber.length == 0 )
			vnt_surchargeNumber.putNoParam();
		else
			vnt_surchargeNumber.putStringRef(surchargeNumber[0]);

		Variant vnt_totalSurcharge = new Variant();
		if( totalSurcharge == null || totalSurcharge.length == 0 )
			vnt_totalSurcharge.putNoParam();
		else
			vnt_totalSurcharge.putStringRef(totalSurcharge[0]);

		Variant vnt_voidsNumber = new Variant();
		if( voidsNumber == null || voidsNumber.length == 0 )
			vnt_voidsNumber.putNoParam();
		else
			vnt_voidsNumber.putStringRef(voidsNumber[0]);

		Variant vnt_voidsTotal = new Variant();
		if( voidsTotal == null || voidsTotal.length == 0 )
			vnt_voidsTotal.putNoParam();
		else
			vnt_voidsTotal.putStringRef(voidsTotal[0]);

		Variant vnt_operatorName = new Variant();
		if( operatorName == null || operatorName.length == 0 )
			vnt_operatorName.putNoParam();
		else
			vnt_operatorName.putStringRef(operatorName[0]);

		Variant vnt_operatorPassword = new Variant();
		if( operatorPassword == null || operatorPassword.length == 0 )
			vnt_operatorPassword.putNoParam();
		else
			vnt_operatorPassword.putStringRef(operatorPassword[0]);

		int result_of_CMD_112_0_0 = Dispatch.callN(this, "CMD_112_0_0", new Object[] { operatorNumber, vnt_receiptsNumber, vnt_registeredSalesNumber, vnt_totalAccumulatedSum, vnt_discountsNumber, vnt_totalDiscounts, vnt_surchargeNumber, vnt_totalSurcharge, vnt_voidsNumber, vnt_voidsTotal, vnt_operatorName, vnt_operatorPassword}).changeType(Variant.VariantInt).getInt();

		if( receiptsNumber != null && receiptsNumber.length > 0 )
			receiptsNumber[0] = vnt_receiptsNumber.toString();
		if( registeredSalesNumber != null && registeredSalesNumber.length > 0 )
			registeredSalesNumber[0] = vnt_registeredSalesNumber.toString();
		if( totalAccumulatedSum != null && totalAccumulatedSum.length > 0 )
			totalAccumulatedSum[0] = vnt_totalAccumulatedSum.toString();
		if( discountsNumber != null && discountsNumber.length > 0 )
			discountsNumber[0] = vnt_discountsNumber.toString();
		if( totalDiscounts != null && totalDiscounts.length > 0 )
			totalDiscounts[0] = vnt_totalDiscounts.toString();
		if( surchargeNumber != null && surchargeNumber.length > 0 )
			surchargeNumber[0] = vnt_surchargeNumber.toString();
		if( totalSurcharge != null && totalSurcharge.length > 0 )
			totalSurcharge[0] = vnt_totalSurcharge.toString();
		if( voidsNumber != null && voidsNumber.length > 0 )
			voidsNumber[0] = vnt_voidsNumber.toString();
		if( voidsTotal != null && voidsTotal.length > 0 )
			voidsTotal[0] = vnt_voidsTotal.toString();
		if( operatorName != null && operatorName.length > 0 )
			operatorName[0] = vnt_operatorName.toString();
		if( operatorPassword != null && operatorPassword.length > 0 )
			operatorPassword[0] = vnt_operatorPassword.toString();

		return result_of_CMD_112_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param docNum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_113_0_0(String docNum) {
		return Dispatch.call(this, "CMD_113_0_0", docNum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param docNum is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_113_0_0(String[] docNum) {
		Variant vnt_docNum = new Variant();
		if( docNum == null || docNum.length == 0 )
			vnt_docNum.putNoParam();
		else
			vnt_docNum.putStringRef(docNum[0]);

		int result_of_CMD_113_0_0 = Dispatch.call(this, "CMD_113_0_0", vnt_docNum).changeType(Variant.VariantInt).getInt();

		if( docNum != null && docNum.length > 0 )
			docNum[0] = vnt_docNum.toString();

		return result_of_CMD_113_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param decRec an input-parameter of type String
	 * @param decimalsCount an input-parameter of type String
	 * @param vATEnabled an input-parameter of type String
	 * @param percA an input-parameter of type String
	 * @param percB an input-parameter of type String
	 * @param percC an input-parameter of type String
	 * @param percD an input-parameter of type String
	 * @param percE an input-parameter of type String
	 * @param percF an input-parameter of type String
	 * @param percG an input-parameter of type String
	 * @param percH an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_0_0(String closure1, String errorCode, String decRec, String decimalsCount, String vATEnabled, String percA, String percB, String percC, String percD, String percE, String percF, String percG, String percH, String dT) {
		return Dispatch.callN(this, "CMD_114_0_0", new Object[] { closure1, errorCode, decRec, decimalsCount, vATEnabled, percA, percB, percC, percD, percE, percF, percG, percH, dT}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param decRec is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param decimalsCount is an one-element array which sends the input-parameter
	 *                      to the ActiveX-Component and receives the output-parameter
	 * @param vATEnabled is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param percA is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percB is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percC is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percD is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percE is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percF is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percG is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percH is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_0_0(String closure1, String[] errorCode, String[] decRec, String[] decimalsCount, String[] vATEnabled, String[] percA, String[] percB, String[] percC, String[] percD, String[] percE, String[] percF, String[] percG, String[] percH, String[] dT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_decRec = new Variant();
		if( decRec == null || decRec.length == 0 )
			vnt_decRec.putNoParam();
		else
			vnt_decRec.putStringRef(decRec[0]);

		Variant vnt_decimalsCount = new Variant();
		if( decimalsCount == null || decimalsCount.length == 0 )
			vnt_decimalsCount.putNoParam();
		else
			vnt_decimalsCount.putStringRef(decimalsCount[0]);

		Variant vnt_vATEnabled = new Variant();
		if( vATEnabled == null || vATEnabled.length == 0 )
			vnt_vATEnabled.putNoParam();
		else
			vnt_vATEnabled.putStringRef(vATEnabled[0]);

		Variant vnt_percA = new Variant();
		if( percA == null || percA.length == 0 )
			vnt_percA.putNoParam();
		else
			vnt_percA.putStringRef(percA[0]);

		Variant vnt_percB = new Variant();
		if( percB == null || percB.length == 0 )
			vnt_percB.putNoParam();
		else
			vnt_percB.putStringRef(percB[0]);

		Variant vnt_percC = new Variant();
		if( percC == null || percC.length == 0 )
			vnt_percC.putNoParam();
		else
			vnt_percC.putStringRef(percC[0]);

		Variant vnt_percD = new Variant();
		if( percD == null || percD.length == 0 )
			vnt_percD.putNoParam();
		else
			vnt_percD.putStringRef(percD[0]);

		Variant vnt_percE = new Variant();
		if( percE == null || percE.length == 0 )
			vnt_percE.putNoParam();
		else
			vnt_percE.putStringRef(percE[0]);

		Variant vnt_percF = new Variant();
		if( percF == null || percF.length == 0 )
			vnt_percF.putNoParam();
		else
			vnt_percF.putStringRef(percF[0]);

		Variant vnt_percG = new Variant();
		if( percG == null || percG.length == 0 )
			vnt_percG.putNoParam();
		else
			vnt_percG.putStringRef(percG[0]);

		Variant vnt_percH = new Variant();
		if( percH == null || percH.length == 0 )
			vnt_percH.putNoParam();
		else
			vnt_percH.putStringRef(percH[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		int result_of_CMD_114_0_0 = Dispatch.callN(this, "CMD_114_0_0", new Object[] { closure1, vnt_errorCode, vnt_decRec, vnt_decimalsCount, vnt_vATEnabled, vnt_percA, vnt_percB, vnt_percC, vnt_percD, vnt_percE, vnt_percF, vnt_percG, vnt_percH, vnt_dT}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( decRec != null && decRec.length > 0 )
			decRec[0] = vnt_decRec.toString();
		if( decimalsCount != null && decimalsCount.length > 0 )
			decimalsCount[0] = vnt_decimalsCount.toString();
		if( vATEnabled != null && vATEnabled.length > 0 )
			vATEnabled[0] = vnt_vATEnabled.toString();
		if( percA != null && percA.length > 0 )
			percA[0] = vnt_percA.toString();
		if( percB != null && percB.length > 0 )
			percB[0] = vnt_percB.toString();
		if( percC != null && percC.length > 0 )
			percC[0] = vnt_percC.toString();
		if( percD != null && percD.length > 0 )
			percD[0] = vnt_percD.toString();
		if( percE != null && percE.length > 0 )
			percE[0] = vnt_percE.toString();
		if( percF != null && percF.length > 0 )
			percF[0] = vnt_percF.toString();
		if( percG != null && percG.length > 0 )
			percG[0] = vnt_percG.toString();
		if( percH != null && percH.length > 0 )
			percH[0] = vnt_percH.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();

		return result_of_CMD_114_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
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
	public int cMD_114_1_0(String closure1, String errorCode, String closure, String receipts, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_114_1_0", new Object[] { closure1, errorCode, closure, receipts, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_114_1_0(String closure1, String[] errorCode, String[] closure, String[] receipts, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

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

		int result_of_CMD_114_1_0 = Dispatch.callN(this, "CMD_114_1_0", new Object[] { closure1, vnt_errorCode, vnt_closure, vnt_receipts, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
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

		return result_of_CMD_114_1_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
	 * @param netA an input-parameter of type String
	 * @param netB an input-parameter of type String
	 * @param netC an input-parameter of type String
	 * @param netD an input-parameter of type String
	 * @param netE an input-parameter of type String
	 * @param netF an input-parameter of type String
	 * @param netG an input-parameter of type String
	 * @param netH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_2_0(String closure1, String errorCode, String closure, String receipts, String netA, String netB, String netC, String netD, String netE, String netF, String netG, String netH) {
		return Dispatch.callN(this, "CMD_114_2_0", new Object[] { closure1, errorCode, closure, receipts, netA, netB, netC, netD, netE, netF, netG, netH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param netA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_2_0(String closure1, String[] errorCode, String[] closure, String[] receipts, String[] netA, String[] netB, String[] netC, String[] netD, String[] netE, String[] netF, String[] netG, String[] netH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

		Variant vnt_netA = new Variant();
		if( netA == null || netA.length == 0 )
			vnt_netA.putNoParam();
		else
			vnt_netA.putStringRef(netA[0]);

		Variant vnt_netB = new Variant();
		if( netB == null || netB.length == 0 )
			vnt_netB.putNoParam();
		else
			vnt_netB.putStringRef(netB[0]);

		Variant vnt_netC = new Variant();
		if( netC == null || netC.length == 0 )
			vnt_netC.putNoParam();
		else
			vnt_netC.putStringRef(netC[0]);

		Variant vnt_netD = new Variant();
		if( netD == null || netD.length == 0 )
			vnt_netD.putNoParam();
		else
			vnt_netD.putStringRef(netD[0]);

		Variant vnt_netE = new Variant();
		if( netE == null || netE.length == 0 )
			vnt_netE.putNoParam();
		else
			vnt_netE.putStringRef(netE[0]);

		Variant vnt_netF = new Variant();
		if( netF == null || netF.length == 0 )
			vnt_netF.putNoParam();
		else
			vnt_netF.putStringRef(netF[0]);

		Variant vnt_netG = new Variant();
		if( netG == null || netG.length == 0 )
			vnt_netG.putNoParam();
		else
			vnt_netG.putStringRef(netG[0]);

		Variant vnt_netH = new Variant();
		if( netH == null || netH.length == 0 )
			vnt_netH.putNoParam();
		else
			vnt_netH.putStringRef(netH[0]);

		int result_of_CMD_114_2_0 = Dispatch.callN(this, "CMD_114_2_0", new Object[] { closure1, vnt_errorCode, vnt_closure, vnt_receipts, vnt_netA, vnt_netB, vnt_netC, vnt_netD, vnt_netE, vnt_netF, vnt_netG, vnt_netH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
		if( netA != null && netA.length > 0 )
			netA[0] = vnt_netA.toString();
		if( netB != null && netB.length > 0 )
			netB[0] = vnt_netB.toString();
		if( netC != null && netC.length > 0 )
			netC[0] = vnt_netC.toString();
		if( netD != null && netD.length > 0 )
			netD[0] = vnt_netD.toString();
		if( netE != null && netE.length > 0 )
			netE[0] = vnt_netE.toString();
		if( netF != null && netF.length > 0 )
			netF[0] = vnt_netF.toString();
		if( netG != null && netG.length > 0 )
			netG[0] = vnt_netG.toString();
		if( netH != null && netH.length > 0 )
			netH[0] = vnt_netH.toString();

		return result_of_CMD_114_2_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
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
	public int cMD_114_3_0(String closure1, String errorCode, String closure, String receipts, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_114_3_0", new Object[] { closure1, errorCode, closure, receipts, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
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
	public int cMD_114_3_0(String closure1, String[] errorCode, String[] closure, String[] receipts, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

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

		int result_of_CMD_114_3_0 = Dispatch.callN(this, "CMD_114_3_0", new Object[] { closure1, vnt_errorCode, vnt_closure, vnt_receipts, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
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

		return result_of_CMD_114_3_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
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
	public int cMD_114_1_1(String closure1, String closure2, String errorCode, String closure, String receipts, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_114_1_1", new Object[] { closure1, closure2, errorCode, closure, receipts, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_114_1_1(String closure1, String closure2, String[] errorCode, String[] closure, String[] receipts, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

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

		int result_of_CMD_114_1_1 = Dispatch.callN(this, "CMD_114_1_1", new Object[] { closure1, closure2, vnt_errorCode, vnt_closure, vnt_receipts, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
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

		return result_of_CMD_114_1_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
	 * @param netA an input-parameter of type String
	 * @param netB an input-parameter of type String
	 * @param netC an input-parameter of type String
	 * @param netD an input-parameter of type String
	 * @param netE an input-parameter of type String
	 * @param netF an input-parameter of type String
	 * @param netG an input-parameter of type String
	 * @param netH an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_2_1(String closure1, String closure2, String errorCode, String closure, String receipts, String netA, String netB, String netC, String netD, String netE, String netF, String netG, String netH) {
		return Dispatch.callN(this, "CMD_114_2_1", new Object[] { closure1, closure2, errorCode, closure, receipts, netA, netB, netC, netD, netE, netF, netG, netH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param netA is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netB is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netC is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netD is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netE is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netF is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netG is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @param netH is an one-element array which sends the input-parameter
	 *             to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_2_1(String closure1, String closure2, String[] errorCode, String[] closure, String[] receipts, String[] netA, String[] netB, String[] netC, String[] netD, String[] netE, String[] netF, String[] netG, String[] netH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

		Variant vnt_netA = new Variant();
		if( netA == null || netA.length == 0 )
			vnt_netA.putNoParam();
		else
			vnt_netA.putStringRef(netA[0]);

		Variant vnt_netB = new Variant();
		if( netB == null || netB.length == 0 )
			vnt_netB.putNoParam();
		else
			vnt_netB.putStringRef(netB[0]);

		Variant vnt_netC = new Variant();
		if( netC == null || netC.length == 0 )
			vnt_netC.putNoParam();
		else
			vnt_netC.putStringRef(netC[0]);

		Variant vnt_netD = new Variant();
		if( netD == null || netD.length == 0 )
			vnt_netD.putNoParam();
		else
			vnt_netD.putStringRef(netD[0]);

		Variant vnt_netE = new Variant();
		if( netE == null || netE.length == 0 )
			vnt_netE.putNoParam();
		else
			vnt_netE.putStringRef(netE[0]);

		Variant vnt_netF = new Variant();
		if( netF == null || netF.length == 0 )
			vnt_netF.putNoParam();
		else
			vnt_netF.putStringRef(netF[0]);

		Variant vnt_netG = new Variant();
		if( netG == null || netG.length == 0 )
			vnt_netG.putNoParam();
		else
			vnt_netG.putStringRef(netG[0]);

		Variant vnt_netH = new Variant();
		if( netH == null || netH.length == 0 )
			vnt_netH.putNoParam();
		else
			vnt_netH.putStringRef(netH[0]);

		int result_of_CMD_114_2_1 = Dispatch.callN(this, "CMD_114_2_1", new Object[] { closure1, closure2, vnt_errorCode, vnt_closure, vnt_receipts, vnt_netA, vnt_netB, vnt_netC, vnt_netD, vnt_netE, vnt_netF, vnt_netG, vnt_netH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
		if( netA != null && netA.length > 0 )
			netA[0] = vnt_netA.toString();
		if( netB != null && netB.length > 0 )
			netB[0] = vnt_netB.toString();
		if( netC != null && netC.length > 0 )
			netC[0] = vnt_netC.toString();
		if( netD != null && netD.length > 0 )
			netD[0] = vnt_netD.toString();
		if( netE != null && netE.length > 0 )
			netE[0] = vnt_netE.toString();
		if( netF != null && netF.length > 0 )
			netF[0] = vnt_netF.toString();
		if( netG != null && netG.length > 0 )
			netG[0] = vnt_netG.toString();
		if( netH != null && netH.length > 0 )
			netH[0] = vnt_netH.toString();

		return result_of_CMD_114_2_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param receipts an input-parameter of type String
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
	public int cMD_114_3_1(String closure1, String closure2, String errorCode, String closure, String receipts, String taxA, String taxB, String taxC, String taxD, String taxE, String taxF, String taxG, String taxH) {
		return Dispatch.callN(this, "CMD_114_3_1", new Object[] { closure1, closure2, errorCode, closure, receipts, taxA, taxB, taxC, taxD, taxE, taxF, taxG, taxH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param closure2 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param receipts is an one-element array which sends the input-parameter
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
	public int cMD_114_3_1(String closure1, String closure2, String[] errorCode, String[] closure, String[] receipts, String[] taxA, String[] taxB, String[] taxC, String[] taxD, String[] taxE, String[] taxF, String[] taxG, String[] taxH) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_receipts = new Variant();
		if( receipts == null || receipts.length == 0 )
			vnt_receipts.putNoParam();
		else
			vnt_receipts.putStringRef(receipts[0]);

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

		int result_of_CMD_114_3_1 = Dispatch.callN(this, "CMD_114_3_1", new Object[] { closure1, closure2, vnt_errorCode, vnt_closure, vnt_receipts, vnt_taxA, vnt_taxB, vnt_taxC, vnt_taxD, vnt_taxE, vnt_taxF, vnt_taxG, vnt_taxH}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( receipts != null && receipts.length > 0 )
			receipts[0] = vnt_receipts.toString();
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

		return result_of_CMD_114_3_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param decRec an input-parameter of type String
	 * @param resetRec an input-parameter of type String
	 * @param kLEN_NUM an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_4_0(String closure1, String errorCode, String closure, String decRec, String resetRec, String kLEN_NUM, String dT) {
		return Dispatch.call(this, "CMD_114_4_0", closure1, errorCode, closure, decRec, resetRec, kLEN_NUM, dT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param decRec is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @param resetRec is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param kLEN_NUM is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_4_0(String closure1, String[] errorCode, String[] closure, String[] decRec, String[] resetRec, String[] kLEN_NUM, String[] dT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_decRec = new Variant();
		if( decRec == null || decRec.length == 0 )
			vnt_decRec.putNoParam();
		else
			vnt_decRec.putStringRef(decRec[0]);

		Variant vnt_resetRec = new Variant();
		if( resetRec == null || resetRec.length == 0 )
			vnt_resetRec.putNoParam();
		else
			vnt_resetRec.putStringRef(resetRec[0]);

		Variant vnt_kLEN_NUM = new Variant();
		if( kLEN_NUM == null || kLEN_NUM.length == 0 )
			vnt_kLEN_NUM.putNoParam();
		else
			vnt_kLEN_NUM.putStringRef(kLEN_NUM[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		int result_of_CMD_114_4_0 = Dispatch.call(this, "CMD_114_4_0", closure1, vnt_errorCode, vnt_closure, vnt_decRec, vnt_resetRec, vnt_kLEN_NUM, vnt_dT).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( decRec != null && decRec.length > 0 )
			decRec[0] = vnt_decRec.toString();
		if( resetRec != null && resetRec.length > 0 )
			resetRec[0] = vnt_resetRec.toString();
		if( kLEN_NUM != null && kLEN_NUM.length > 0 )
			kLEN_NUM[0] = vnt_kLEN_NUM.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();

		return result_of_CMD_114_4_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param decimalsCount an input-parameter of type String
	 * @param vATEnabled an input-parameter of type String
	 * @param percA an input-parameter of type String
	 * @param percB an input-parameter of type String
	 * @param percC an input-parameter of type String
	 * @param percD an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_5_0(String closure1, String errorCode, String decimalsCount, String vATEnabled, String percA, String percB, String percC, String percD, String dT) {
		return Dispatch.callN(this, "CMD_114_5_0", new Object[] { closure1, errorCode, decimalsCount, vATEnabled, percA, percB, percC, percD, dT}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param decimalsCount is an one-element array which sends the input-parameter
	 *                      to the ActiveX-Component and receives the output-parameter
	 * @param vATEnabled is an one-element array which sends the input-parameter
	 *                   to the ActiveX-Component and receives the output-parameter
	 * @param percA is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percB is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percC is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param percD is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_5_0(String closure1, String[] errorCode, String[] decimalsCount, String[] vATEnabled, String[] percA, String[] percB, String[] percC, String[] percD, String[] dT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_decimalsCount = new Variant();
		if( decimalsCount == null || decimalsCount.length == 0 )
			vnt_decimalsCount.putNoParam();
		else
			vnt_decimalsCount.putStringRef(decimalsCount[0]);

		Variant vnt_vATEnabled = new Variant();
		if( vATEnabled == null || vATEnabled.length == 0 )
			vnt_vATEnabled.putNoParam();
		else
			vnt_vATEnabled.putStringRef(vATEnabled[0]);

		Variant vnt_percA = new Variant();
		if( percA == null || percA.length == 0 )
			vnt_percA.putNoParam();
		else
			vnt_percA.putStringRef(percA[0]);

		Variant vnt_percB = new Variant();
		if( percB == null || percB.length == 0 )
			vnt_percB.putNoParam();
		else
			vnt_percB.putStringRef(percB[0]);

		Variant vnt_percC = new Variant();
		if( percC == null || percC.length == 0 )
			vnt_percC.putNoParam();
		else
			vnt_percC.putStringRef(percC[0]);

		Variant vnt_percD = new Variant();
		if( percD == null || percD.length == 0 )
			vnt_percD.putNoParam();
		else
			vnt_percD.putStringRef(percD[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		int result_of_CMD_114_5_0 = Dispatch.callN(this, "CMD_114_5_0", new Object[] { closure1, vnt_errorCode, vnt_decimalsCount, vnt_vATEnabled, vnt_percA, vnt_percB, vnt_percC, vnt_percD, vnt_dT}).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( decimalsCount != null && decimalsCount.length > 0 )
			decimalsCount[0] = vnt_decimalsCount.toString();
		if( vATEnabled != null && vATEnabled.length > 0 )
			vATEnabled[0] = vnt_vATEnabled.toString();
		if( percA != null && percA.length > 0 )
			percA[0] = vnt_percA.toString();
		if( percB != null && percB.length > 0 )
			percB[0] = vnt_percB.toString();
		if( percC != null && percC.length > 0 )
			percC[0] = vnt_percC.toString();
		if( percD != null && percD.length > 0 )
			percD[0] = vnt_percD.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();

		return result_of_CMD_114_5_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_114_6_0(String closure1, String errorCode, String dT) {
		return Dispatch.call(this, "CMD_114_6_0", closure1, errorCode, dT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param closure1 an input-parameter of type String
	 * @param errorCode is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_114_6_0(String closure1, String[] errorCode, String[] dT) {
		Variant vnt_errorCode = new Variant();
		if( errorCode == null || errorCode.length == 0 )
			vnt_errorCode.putNoParam();
		else
			vnt_errorCode.putStringRef(errorCode[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		int result_of_CMD_114_6_0 = Dispatch.call(this, "CMD_114_6_0", closure1, vnt_errorCode, vnt_dT).changeType(Variant.VariantInt).getInt();

		if( errorCode != null && errorCode.length > 0 )
			errorCode[0] = vnt_errorCode.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();

		return result_of_CMD_114_6_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param rowNum an input-parameter of type String
	 * @param rowData an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_115_0_0(String rowNum, String rowData) {
		return Dispatch.call(this, "CMD_115_0_0", rowNum, rowData).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param rowNum an input-parameter of type String
	 * @param rowData an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_115_1_0(String rowNum, String rowData) {
		return Dispatch.call(this, "CMD_115_1_0", rowNum, rowData).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param rowNum an input-parameter of type String
	 * @param rowData is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_115_1_0(String rowNum, String[] rowData) {
		Variant vnt_rowData = new Variant();
		if( rowData == null || rowData.length == 0 )
			vnt_rowData.putNoParam();
		else
			vnt_rowData.putStringRef(rowData[0]);

		int result_of_CMD_115_1_0 = Dispatch.call(this, "CMD_115_1_0", rowNum, vnt_rowData).changeType(Variant.VariantInt).getInt();

		if( rowData != null && rowData.length > 0 )
			rowData[0] = vnt_rowData.toString();

		return result_of_CMD_115_1_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param fM_Total an input-parameter of type String
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
	public int cMD_117_0_0(String option, String n, String closure, String fM_Total, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_117_0_0", new Object[] { option, n, closure, fM_Total, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_117_0_0(String option, String n, String[] closure, String[] fM_Total, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putStringRef(fM_Total[0]);

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

		int result_of_CMD_117_0_0 = Dispatch.callN(this, "CMD_117_0_0", new Object[] { option, n, vnt_closure, vnt_fM_Total, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toString();
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

		return result_of_CMD_117_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure an input-parameter of type String
	 * @param fM_Total an input-parameter of type String
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
	public int cMD_118_0_0(String option, String n, String closure, String fM_Total, String totA, String totB, String totC, String totD, String totE, String totF, String totG, String totH) {
		return Dispatch.callN(this, "CMD_118_0_0", new Object[] { option, n, closure, fM_Total, totA, totB, totC, totD, totE, totF, totG, totH}).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param option an input-parameter of type String
	 * @param n an input-parameter of type String
	 * @param closure is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param fM_Total is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
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
	public int cMD_118_0_0(String option, String n, String[] closure, String[] fM_Total, String[] totA, String[] totB, String[] totC, String[] totD, String[] totE, String[] totF, String[] totG, String[] totH) {
		Variant vnt_closure = new Variant();
		if( closure == null || closure.length == 0 )
			vnt_closure.putNoParam();
		else
			vnt_closure.putStringRef(closure[0]);

		Variant vnt_fM_Total = new Variant();
		if( fM_Total == null || fM_Total.length == 0 )
			vnt_fM_Total.putNoParam();
		else
			vnt_fM_Total.putStringRef(fM_Total[0]);

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

		int result_of_CMD_118_0_0 = Dispatch.callN(this, "CMD_118_0_0", new Object[] { option, n, vnt_closure, vnt_fM_Total, vnt_totA, vnt_totB, vnt_totC, vnt_totD, vnt_totE, vnt_totF, vnt_totG, vnt_totH}).changeType(Variant.VariantInt).getInt();

		if( closure != null && closure.length > 0 )
			closure[0] = vnt_closure.toString();
		if( fM_Total != null && fM_Total.length > 0 )
			fM_Total[0] = vnt_fM_Total.toString();
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

		return result_of_CMD_118_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int cMD_119_0_0() {
		return Dispatch.call(this, "CMD_119_0_0").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param z_NUM an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param fDocs an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @param sK an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_0_1(String z_NUM, String f_RESULT, String fDocs, String dT, String sK) {
		return Dispatch.call(this, "CMD_119_0_1", z_NUM, f_RESULT, fDocs, dT, sK).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param z_NUM an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param fDocs is an one-element array which sends the input-parameter
	 *              to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param sK is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_0_1(String z_NUM, String[] f_RESULT, String[] fDocs, String[] dT, String[] sK) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_fDocs = new Variant();
		if( fDocs == null || fDocs.length == 0 )
			vnt_fDocs.putNoParam();
		else
			vnt_fDocs.putStringRef(fDocs[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		Variant vnt_sK = new Variant();
		if( sK == null || sK.length == 0 )
			vnt_sK.putNoParam();
		else
			vnt_sK.putStringRef(sK[0]);

		int result_of_CMD_119_0_1 = Dispatch.call(this, "CMD_119_0_1", z_NUM, vnt_f_RESULT, vnt_fDocs, vnt_dT, vnt_sK).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( fDocs != null && fDocs.length > 0 )
			fDocs[0] = vnt_fDocs.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();
		if( sK != null && sK.length > 0 )
			sK[0] = vnt_sK.toString();

		return result_of_CMD_119_0_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param z_NUM an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param doc_Num an input-parameter of type String
	 * @param dT an input-parameter of type String
	 * @param sK an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_0_2(String z_NUM, String f_RESULT, String doc_Num, String dT, String sK) {
		return Dispatch.call(this, "CMD_119_0_2", z_NUM, f_RESULT, doc_Num, dT, sK).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param z_NUM an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param doc_Num is an one-element array which sends the input-parameter
	 *                to the ActiveX-Component and receives the output-parameter
	 * @param dT is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param sK is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_0_2(String z_NUM, String[] f_RESULT, String[] doc_Num, String[] dT, String[] sK) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_doc_Num = new Variant();
		if( doc_Num == null || doc_Num.length == 0 )
			vnt_doc_Num.putNoParam();
		else
			vnt_doc_Num.putStringRef(doc_Num[0]);

		Variant vnt_dT = new Variant();
		if( dT == null || dT.length == 0 )
			vnt_dT.putNoParam();
		else
			vnt_dT.putStringRef(dT[0]);

		Variant vnt_sK = new Variant();
		if( sK == null || sK.length == 0 )
			vnt_sK.putNoParam();
		else
			vnt_sK.putStringRef(sK[0]);

		int result_of_CMD_119_0_2 = Dispatch.call(this, "CMD_119_0_2", z_NUM, vnt_f_RESULT, vnt_doc_Num, vnt_dT, vnt_sK).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( doc_Num != null && doc_Num.length > 0 )
			doc_Num[0] = vnt_doc_Num.toString();
		if( dT != null && dT.length > 0 )
			dT[0] = vnt_dT.toString();
		if( sK != null && sK.length > 0 )
			sK[0] = vnt_sK.toString();

		return result_of_CMD_119_0_2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param f_RESULT an input-parameter of type String
	 * @param tot_Size an input-parameter of type String
	 * @param used_Size an input-parameter of type String
	 * @param c1 an input-parameter of type String
	 * @param c2 an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_1_0(String f_RESULT, String tot_Size, String used_Size, String c1, String c2, String d1, String d2) {
		return Dispatch.call(this, "CMD_119_1_0", f_RESULT, tot_Size, used_Size, c1, c2, d1, d2).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tot_Size is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param used_Size is an one-element array which sends the input-parameter
	 *                  to the ActiveX-Component and receives the output-parameter
	 * @param c1 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param c2 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param d1 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @param d2 is an one-element array which sends the input-parameter
	 *           to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_1_0(String[] f_RESULT, String[] tot_Size, String[] used_Size, String[] c1, String[] c2, String[] d1, String[] d2) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tot_Size = new Variant();
		if( tot_Size == null || tot_Size.length == 0 )
			vnt_tot_Size.putNoParam();
		else
			vnt_tot_Size.putStringRef(tot_Size[0]);

		Variant vnt_used_Size = new Variant();
		if( used_Size == null || used_Size.length == 0 )
			vnt_used_Size.putNoParam();
		else
			vnt_used_Size.putStringRef(used_Size[0]);

		Variant vnt_c1 = new Variant();
		if( c1 == null || c1.length == 0 )
			vnt_c1.putNoParam();
		else
			vnt_c1.putStringRef(c1[0]);

		Variant vnt_c2 = new Variant();
		if( c2 == null || c2.length == 0 )
			vnt_c2.putNoParam();
		else
			vnt_c2.putStringRef(c2[0]);

		Variant vnt_d1 = new Variant();
		if( d1 == null || d1.length == 0 )
			vnt_d1.putNoParam();
		else
			vnt_d1.putStringRef(d1[0]);

		Variant vnt_d2 = new Variant();
		if( d2 == null || d2.length == 0 )
			vnt_d2.putNoParam();
		else
			vnt_d2.putStringRef(d2[0]);

		int result_of_CMD_119_1_0 = Dispatch.call(this, "CMD_119_1_0", vnt_f_RESULT, vnt_tot_Size, vnt_used_Size, vnt_c1, vnt_c2, vnt_d1, vnt_d2).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tot_Size != null && tot_Size.length > 0 )
			tot_Size[0] = vnt_tot_Size.toString();
		if( used_Size != null && used_Size.length > 0 )
			used_Size[0] = vnt_used_Size.toString();
		if( c1 != null && c1.length > 0 )
			c1[0] = vnt_c1.toString();
		if( c2 != null && c2.length > 0 )
			c2[0] = vnt_c2.toString();
		if( d1 != null && d1.length > 0 )
			d1[0] = vnt_d1.toString();
		if( d2 != null && d2.length > 0 )
			d2[0] = vnt_d2.toString();

		return result_of_CMD_119_1_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_2_0(String d1, String d2, String f_RESULT, String tHE_TEXT) {
		return Dispatch.call(this, "CMD_119_2_0", d1, d2, f_RESULT, tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tHE_TEXT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_2_0(String d1, String d2, String[] f_RESULT, String[] tHE_TEXT) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tHE_TEXT = new Variant();
		if( tHE_TEXT == null || tHE_TEXT.length == 0 )
			vnt_tHE_TEXT.putNoParam();
		else
			vnt_tHE_TEXT.putStringRef(tHE_TEXT[0]);

		int result_of_CMD_119_2_0 = Dispatch.call(this, "CMD_119_2_0", d1, d2, vnt_f_RESULT, vnt_tHE_TEXT).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tHE_TEXT != null && tHE_TEXT.length > 0 )
			tHE_TEXT[0] = vnt_tHE_TEXT.toString();

		return result_of_CMD_119_2_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_2_1(String cl, String d1, String d2, String f_RESULT, String tHE_TEXT) {
		return Dispatch.call(this, "CMD_119_2_1", cl, d1, d2, f_RESULT, tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tHE_TEXT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_2_1(String cl, String d1, String d2, String[] f_RESULT, String[] tHE_TEXT) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tHE_TEXT = new Variant();
		if( tHE_TEXT == null || tHE_TEXT.length == 0 )
			vnt_tHE_TEXT.putNoParam();
		else
			vnt_tHE_TEXT.putStringRef(tHE_TEXT[0]);

		int result_of_CMD_119_2_1 = Dispatch.call(this, "CMD_119_2_1", cl, d1, d2, vnt_f_RESULT, vnt_tHE_TEXT).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tHE_TEXT != null && tHE_TEXT.length > 0 )
			tHE_TEXT[0] = vnt_tHE_TEXT.toString();

		return result_of_CMD_119_2_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_2_2(String cl, String f_RESULT, String tHE_TEXT) {
		return Dispatch.call(this, "CMD_119_2_2", cl, f_RESULT, tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tHE_TEXT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_2_2(String cl, String[] f_RESULT, String[] tHE_TEXT) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tHE_TEXT = new Variant();
		if( tHE_TEXT == null || tHE_TEXT.length == 0 )
			vnt_tHE_TEXT.putNoParam();
		else
			vnt_tHE_TEXT.putStringRef(tHE_TEXT[0]);

		int result_of_CMD_119_2_2 = Dispatch.call(this, "CMD_119_2_2", cl, vnt_f_RESULT, vnt_tHE_TEXT).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tHE_TEXT != null && tHE_TEXT.length > 0 )
			tHE_TEXT[0] = vnt_tHE_TEXT.toString();

		return result_of_CMD_119_2_2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param f_RESULT an input-parameter of type String
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_2_3(String flg, String dT1, String dT2, String f_RESULT, String tHE_TEXT) {
		return Dispatch.call(this, "CMD_119_2_3", flg, dT1, dT2, f_RESULT, tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tHE_TEXT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_2_3(String flg, String dT1, String dT2, String[] f_RESULT, String[] tHE_TEXT) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tHE_TEXT = new Variant();
		if( tHE_TEXT == null || tHE_TEXT.length == 0 )
			vnt_tHE_TEXT.putNoParam();
		else
			vnt_tHE_TEXT.putStringRef(tHE_TEXT[0]);

		int result_of_CMD_119_2_3 = Dispatch.call(this, "CMD_119_2_3", flg, dT1, dT2, vnt_f_RESULT, vnt_tHE_TEXT).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tHE_TEXT != null && tHE_TEXT.length > 0 )
			tHE_TEXT[0] = vnt_tHE_TEXT.toString();

		return result_of_CMD_119_2_3;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param f_RESULT an input-parameter of type String
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_3_0(String f_RESULT, String tHE_TEXT) {
		return Dispatch.call(this, "CMD_119_3_0", f_RESULT, tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param f_RESULT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @param tHE_TEXT is an one-element array which sends the input-parameter
	 *                 to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_3_0(String[] f_RESULT, String[] tHE_TEXT) {
		Variant vnt_f_RESULT = new Variant();
		if( f_RESULT == null || f_RESULT.length == 0 )
			vnt_f_RESULT.putNoParam();
		else
			vnt_f_RESULT.putStringRef(f_RESULT[0]);

		Variant vnt_tHE_TEXT = new Variant();
		if( tHE_TEXT == null || tHE_TEXT.length == 0 )
			vnt_tHE_TEXT.putNoParam();
		else
			vnt_tHE_TEXT.putStringRef(tHE_TEXT[0]);

		int result_of_CMD_119_3_0 = Dispatch.call(this, "CMD_119_3_0", vnt_f_RESULT, vnt_tHE_TEXT).changeType(Variant.VariantInt).getInt();

		if( f_RESULT != null && f_RESULT.length > 0 )
			f_RESULT[0] = vnt_f_RESULT.toString();
		if( tHE_TEXT != null && tHE_TEXT.length > 0 )
			tHE_TEXT[0] = vnt_tHE_TEXT.toString();

		return result_of_CMD_119_3_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_0(String d1, String d2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_0", d1, d2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_0(String d1, String d2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_0 = Dispatch.call(this, "CMD_119_4_0", d1, d2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_1(String d1, String d2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_1", d1, d2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_1(String d1, String d2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_1 = Dispatch.call(this, "CMD_119_4_1", d1, d2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_1;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_2(String cl, String d1, String d2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_2", cl, d1, d2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_2(String cl, String d1, String d2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_2 = Dispatch.call(this, "CMD_119_4_2", cl, d1, d2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_2;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_3(String cl, String d1, String d2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_3", cl, d1, d2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param d1 an input-parameter of type String
	 * @param d2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_3(String cl, String d1, String d2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_3 = Dispatch.call(this, "CMD_119_4_3", cl, d1, d2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_3;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_4(String cl, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_4", cl, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_4(String cl, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_4 = Dispatch.call(this, "CMD_119_4_4", cl, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_4;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_5(String cl, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_5", cl, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param cl an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_5(String cl, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_5 = Dispatch.call(this, "CMD_119_4_5", cl, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_5;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_6(String flg, String dT1, String dT2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_6", flg, dT1, dT2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_6(String flg, String dT1, String dT2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_6 = Dispatch.call(this, "CMD_119_4_6", flg, dT1, dT2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_6;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param printedCount an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_119_4_7(String flg, String dT1, String dT2, String printedCount) {
		return Dispatch.call(this, "CMD_119_4_7", flg, dT1, dT2, printedCount).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param flg an input-parameter of type String
	 * @param dT1 an input-parameter of type String
	 * @param dT2 an input-parameter of type String
	 * @param printedCount is an one-element array which sends the input-parameter
	 *                     to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_119_4_7(String flg, String dT1, String dT2, String[] printedCount) {
		Variant vnt_printedCount = new Variant();
		if( printedCount == null || printedCount.length == 0 )
			vnt_printedCount.putNoParam();
		else
			vnt_printedCount.putStringRef(printedCount[0]);

		int result_of_CMD_119_4_7 = Dispatch.call(this, "CMD_119_4_7", flg, dT1, dT2, vnt_printedCount).changeType(Variant.VariantInt).getInt();

		if( printedCount != null && printedCount.length > 0 )
			printedCount[0] = vnt_printedCount.toString();

		return result_of_CMD_119_4_7;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param rotRec an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_122_0_0(String rotRec) {
		return Dispatch.call(this, "CMD_122_0_0", rotRec).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param rotRec is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_122_0_0(String[] rotRec) {
		Variant vnt_rotRec = new Variant();
		if( rotRec == null || rotRec.length == 0 )
			vnt_rotRec.putNoParam();
		else
			vnt_rotRec.putStringRef(rotRec[0]);

		int result_of_CMD_122_0_0 = Dispatch.call(this, "CMD_122_0_0", vnt_rotRec).changeType(Variant.VariantInt).getInt();

		if( rotRec != null && rotRec.length > 0 )
			rotRec[0] = vnt_rotRec.toString();

		return result_of_CMD_122_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param tHE_TEXT an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_123_0_0(String tHE_TEXT) {
		return Dispatch.call(this, "CMD_123_0_0", tHE_TEXT).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param rotRec an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_124_0_0(String rotRec) {
		return Dispatch.call(this, "CMD_124_0_0", rotRec).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method and receiving the output-parameter(s).
	 * @param rotRec is an one-element array which sends the input-parameter
	 *               to the ActiveX-Component and receives the output-parameter
	 * @return the result is of type int
	 */
	public int cMD_124_0_0(String[] rotRec) {
		Variant vnt_rotRec = new Variant();
		if( rotRec == null || rotRec.length == 0 )
			vnt_rotRec.putNoParam();
		else
			vnt_rotRec.putStringRef(rotRec[0]);

		int result_of_CMD_124_0_0 = Dispatch.call(this, "CMD_124_0_0", vnt_rotRec).changeType(Variant.VariantInt).getInt();

		if( rotRec != null && rotRec.length > 0 )
			rotRec[0] = vnt_rotRec.toString();

		return result_of_CMD_124_0_0;
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param lANG_TRGT an input-parameter of type int
	 * @return the result is of type String
	 */
	public String gET_LASTERROR(int lANG_TRGT) {
		return Dispatch.call(this, "GET_LASTERROR", new Variant(lANG_TRGT)).toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param bYTE_INDEX an input-parameter of type int
	 * @param bIT_INDEX an input-parameter of type int
	 * @param lANG_TRGT an input-parameter of type int
	 * @return the result is of type String
	 */
	public String gET_STATUS_DESCRIPTIONEX(int bYTE_INDEX, int bIT_INDEX, int lANG_TRGT) {
		return Dispatch.call(this, "GET_STATUS_DESCRIPTIONEX", new Variant(bYTE_INDEX), new Variant(bIT_INDEX), new Variant(lANG_TRGT)).toString();
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

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param port an input-parameter of type int
	 * @param boudRate an input-parameter of type int
	 * @param readIntervalTimeout an input-parameter of type int
	 * @param readTotalTimeoutMultiplier an input-parameter of type int
	 * @param readTotalTimeoutConstant an input-parameter of type int
	 * @param writeTotalTimeoutMultiplier an input-parameter of type int
	 * @param writeTotalTimeoutConstant an input-parameter of type int
	 * @param rEPEAT_COUNT an input-parameter of type int
	 * @param moreInstances an input-parameter of type boolean
	 * @param withInputCheck an input-parameter of type boolean
	 * @param withOutpCheck an input-parameter of type boolean
	 * @param alwaysGetOutput an input-parameter of type boolean
	 * @param autoCutText an input-parameter of type boolean
	 */
	public void iNIT_EX3(int port, int boudRate, int readIntervalTimeout, int readTotalTimeoutMultiplier, int readTotalTimeoutConstant, int writeTotalTimeoutMultiplier, int writeTotalTimeoutConstant, int rEPEAT_COUNT, boolean moreInstances, boolean withInputCheck, boolean withOutpCheck, boolean alwaysGetOutput, boolean autoCutText) {
		Dispatch.callN(this, "INIT_EX3", new Object[] { new Variant(port), new Variant(boudRate), readIntervalTimeout, readTotalTimeoutMultiplier, readTotalTimeoutConstant, writeTotalTimeoutMultiplier, writeTotalTimeoutConstant, new Variant(rEPEAT_COUNT), new Variant(moreInstances), new Variant(withInputCheck), new Variant(withOutpCheck), new Variant(alwaysGetOutput), new Variant(autoCutText)});
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_0_3(String taxCd, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_0_3", taxCd, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_1_3(String taxCd, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_1_3", taxCd, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_2_3(String taxCd, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_2_3", taxCd, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_3_3(String dept, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_3_3", dept, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_4_3(String dept, String price, String qwan, String absSum) {
		return Dispatch.call(this, "CMD_49_4_3", dept, price, qwan, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_5_3(String dept, String price, String qwan) {
		return Dispatch.call(this, "CMD_49_5_3", dept, price, qwan).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_6_0(String l1, String l2, String taxCd, String price) {
		return Dispatch.call(this, "CMD_49_6_0", l1, l2, taxCd, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_6_1(String l1, String taxCd, String price) {
		return Dispatch.call(this, "CMD_49_6_1", l1, taxCd, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_6_2(String l2, String taxCd, String price) {
		return Dispatch.call(this, "CMD_49_6_2", l2, taxCd, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_6_3(String taxCd, String price) {
		return Dispatch.call(this, "CMD_49_6_3", taxCd, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_7_0(String l1, String l2, String taxCd, String price, String perc) {
		return Dispatch.call(this, "CMD_49_7_0", l1, l2, taxCd, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_7_1(String l1, String taxCd, String price, String perc) {
		return Dispatch.call(this, "CMD_49_7_1", l1, taxCd, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_7_2(String l2, String taxCd, String price, String perc) {
		return Dispatch.call(this, "CMD_49_7_2", l2, taxCd, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_7_3(String taxCd, String price, String perc) {
		return Dispatch.call(this, "CMD_49_7_3", taxCd, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_8_0(String l1, String l2, String taxCd, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_8_0", l1, l2, taxCd, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_8_1(String l1, String taxCd, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_8_1", l1, taxCd, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_8_2(String l2, String taxCd, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_8_2", l2, taxCd, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_8_3(String taxCd, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_8_3", taxCd, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_9_0(String l1, String l2, String dept, String price) {
		return Dispatch.call(this, "CMD_49_9_0", l1, l2, dept, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_9_1(String l1, String dept, String price) {
		return Dispatch.call(this, "CMD_49_9_1", l1, dept, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_9_2(String l2, String dept, String price) {
		return Dispatch.call(this, "CMD_49_9_2", l2, dept, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_9_3(String dept, String price) {
		return Dispatch.call(this, "CMD_49_9_3", dept, price).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_10_0(String l1, String l2, String dept, String price, String perc) {
		return Dispatch.call(this, "CMD_49_10_0", l1, l2, dept, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_10_1(String l1, String dept, String price, String perc) {
		return Dispatch.call(this, "CMD_49_10_1", l1, dept, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_10_2(String l2, String dept, String price, String perc) {
		return Dispatch.call(this, "CMD_49_10_2", l2, dept, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_10_3(String dept, String price, String perc) {
		return Dispatch.call(this, "CMD_49_10_3", dept, price, perc).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_11_0(String l1, String l2, String dept, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_11_0", l1, l2, dept, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l1 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_11_1(String l1, String dept, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_11_1", l1, dept, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_11_2(String l2, String dept, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_11_2", l2, dept, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param dept an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param absSum an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_11_3(String dept, String price, String absSum) {
		return Dispatch.call(this, "CMD_49_11_3", dept, price, absSum).changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param l2 an input-parameter of type String
	 * @param taxCd an input-parameter of type String
	 * @param price an input-parameter of type String
	 * @param qwan an input-parameter of type String
	 * @param perc an input-parameter of type String
	 * @return the result is of type int
	 */
	public int cMD_49_0_2(String l2, String taxCd, String price, String qwan, String perc) {
		return Dispatch.call(this, "CMD_49_0_2", l2, taxCd, price, qwan, perc).changeType(Variant.VariantInt).getInt();
	}

}
