package entity.databuilder;

uses gw.api.databuilder.DataBuilder
uses typekey.MOJPhaseId_Ins
uses typekey.MOJClaimValue_Ins
uses typekey.SubLossCause_Ins


/**
 * MOJDetailBuilder_Ins - Builder for MOJDetail_Ins entity
 * CV9-102 - MOJ Days Off Work value not populating
 *
 * Auto generated builder class
 * Author: ZEEV
 * Date: 25/04/2022
 * Time: 06:34:34.744 AM
*/
@Export
class MOJDetailBuilder_Ins {

    construct(){
        super(MOJDetail_Ins)
    }

    public function createMOJDetail_InsUtil (currentphase_ins:MOJPhaseId_Ins, claimvalue_ins:typekey.MOJClaimValue_Ins, activityengineguid_ins:String, article75_ins:boolean, 
										fraudcomment_ins:String, notificationdate_ins:Date, splitliabilitydetail_ins:String, tpcontributingneg_ins:int, 
										amountclaimed_ins:int, sublosscause_ins:typekey.SubLossCause_Ins, contnegligence_ins:int, reasonforoverride_ext:String, 
										liabilityadmitdate_ins:Date) : MOJDetail_Ins {

        var _instance = new MOJDetail_Ins()

        _instance.MOJPhaseId_Ins=currentphase_ins
        _instance.MOJClaimValue_Ins=claimvalue_ins
        _instance.ActivityEngineGUID_Ins=activityengineguid_ins
        _instance.Article75_Ins=article75_ins
        _instance.FraudComment_Ins=fraudcomment_ins
        _instance.NotificationDate_Ins=notificationdate_ins
        _instance.SplitLiabilityDetail_Ins=splitliabilitydetail_ins
        _instance.TPContributingNeg_Ins=tpcontributingneg_ins
        _instance.AmountClaimed_Ins=amountclaimed_ins
        _instance.SubLossCause_Ins=sublosscause_ins
        _instance.ContNegligence_Ins=contnegligence_ins
        _instance.ReasonForOverride_Ext=reasonforoverride_ext
        _instance.LiabilityAdmitDate_Ins=liabilityadmitdate_ins


        return _instance;

    }


}

