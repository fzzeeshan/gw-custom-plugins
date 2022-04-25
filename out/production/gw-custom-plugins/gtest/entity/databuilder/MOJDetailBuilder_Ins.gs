package entity.databuilder;

uses gw.api.databuilder.DataBuilder
uses java.lang.Integer
uses java.util.Date
uses typekey.MOJPhaseId_Ins
uses typekey.MOJClaimValue_Ins
uses typekey.SubLossCause_Ins


/**
 * MOJDetailBuilder_Ins - Builder for MOJDetail_Ins entity
 * CV9-102 - MOJ Days Off Work value not populating
 *
 * Auto generated builder class
 * Author: ZEEV
 * Date: 24/04/2022
 * Time: 05:59:23.680 PM
*/
@Export
class MOJDetailBuilder_Ins extends DataBuilder<MOJDetail_Ins, MOJDetailBuilder_Ins> {

    construct(){
        super(MOJDetail_Ins)
    }

    
    /**
      * Field typekey.MOJPhaseId_Ins
      * Sets the value of typekey.MOJPhaseId_Ins for entities created using this builder class
    */
    @Param("typekey.MOJPhaseId_Ins", "Value to be set for typekey.MOJPhaseId_Ins")
    function withMOJPhaseId_Ins (currentphase_ins : typekey.MOJPhaseId_Ins) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("typekey.MOJPhaseId_Ins"), currentphase_ins)
        return this
    }



    /**
      * Field typekey.MOJClaimValue_Ins
      * Sets the value of typekey.MOJClaimValue_Ins for entities created using this builder class
    */
    @Param("typekey.MOJClaimValue_Ins", "Value to be set for typekey.MOJClaimValue_Ins")
    function withMOJClaimValue_Ins (claimvalue_ins : typekey.MOJClaimValue_Ins) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("typekey.MOJClaimValue_Ins"), claimvalue_ins)
        return this
    }



    /**
      * Field ActivityEngineGUID_Ins
      * Sets the value of ActivityEngineGUID_Ins for entities created using this builder class
    */
    @Param("ActivityEngineGUID_Ins", "Value to be set for ActivityEngineGUID_Ins")
    function withActivityEngineGUID_Ins (activityengineguid_ins : String) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("ActivityEngineGUID_Ins"), activityengineguid_ins)
        return this
    }



    /**
      * Field Article75_Ins
      * Sets the value of Article75_Ins for entities created using this builder class
    */
    @Param("Article75_Ins", "Value to be set for Article75_Ins")
    function withArticle75_Ins (article75_ins : boolean) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("Article75_Ins"), article75_ins)
        return this
    }



    /**
      * Field FraudComment_Ins
      * Sets the value of FraudComment_Ins for entities created using this builder class
    */
    @Param("FraudComment_Ins", "Value to be set for FraudComment_Ins")
    function withFraudComment_Ins (fraudcomment_ins : String) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("FraudComment_Ins"), fraudcomment_ins)
        return this
    }



    /**
      * Field NotificationDate_Ins
      * Sets the value of NotificationDate_Ins for entities created using this builder class
    */
    @Param("NotificationDate_Ins", "Value to be set for NotificationDate_Ins")
    function withNotificationDate_Ins (notificationdate_ins : Date) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("NotificationDate_Ins"), notificationdate_ins)
        return this
    }



    /**
      * Field SplitLiabilityDetail_Ins
      * Sets the value of SplitLiabilityDetail_Ins for entities created using this builder class
    */
    @Param("SplitLiabilityDetail_Ins", "Value to be set for SplitLiabilityDetail_Ins")
    function withSplitLiabilityDetail_Ins (splitliabilitydetail_ins : String) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("SplitLiabilityDetail_Ins"), splitliabilitydetail_ins)
        return this
    }



    /**
      * Field TPContributingNeg_Ins
      * Sets the value of TPContributingNeg_Ins for entities created using this builder class
    */
    @Param("TPContributingNeg_Ins", "Value to be set for TPContributingNeg_Ins")
    function withTPContributingNeg_Ins (tpcontributingneg_ins : int) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("TPContributingNeg_Ins"), tpcontributingneg_ins)
        return this
    }



    /**
      * Field AmountClaimed_Ins
      * Sets the value of AmountClaimed_Ins for entities created using this builder class
    */
    @Param("AmountClaimed_Ins", "Value to be set for AmountClaimed_Ins")
    function withAmountClaimed_Ins (amountclaimed_ins : int) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("AmountClaimed_Ins"), amountclaimed_ins)
        return this
    }



    /**
      * Field typekey.SubLossCause_Ins
      * Sets the value of typekey.SubLossCause_Ins for entities created using this builder class
    */
    @Param("typekey.SubLossCause_Ins", "Value to be set for typekey.SubLossCause_Ins")
    function withSubLossCause_Ins (sublosscause_ins : typekey.SubLossCause_Ins) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("typekey.SubLossCause_Ins"), sublosscause_ins)
        return this
    }



    /**
      * Field ContNegligence_Ins
      * Sets the value of ContNegligence_Ins for entities created using this builder class
    */
    @Param("ContNegligence_Ins", "Value to be set for ContNegligence_Ins")
    function withContNegligence_Ins (contnegligence_ins : int) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("ContNegligence_Ins"), contnegligence_ins)
        return this
    }



    /**
      * Field ReasonForOverride_Ext
      * Sets the value of ReasonForOverride_Ext for entities created using this builder class
    */
    @Param("ReasonForOverride_Ext", "Value to be set for ReasonForOverride_Ext")
    function withReasonForOverride_Ext (reasonforoverride_ext : String) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("ReasonForOverride_Ext"), reasonforoverride_ext)
        return this
    }



    /**
      * Field LiabilityAdmitDate_Ins
      * Sets the value of LiabilityAdmitDate_Ins for entities created using this builder class
    */
    @Param("LiabilityAdmitDate_Ins", "Value to be set for LiabilityAdmitDate_Ins")
    function withLiabilityAdmitDate_Ins (liabilityadmitdate_ins : Date) : MOJDetailBuilder_Ins
    {
        this.set(MOJDetail_Ins.Type.TypeInfo.getProperty("LiabilityAdmitDate_Ins"), liabilityadmitdate_ins)
        return this
    }





}

