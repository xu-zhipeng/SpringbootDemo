package com.youjun.api.modules.office.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 诉源表
 * </p>
 *
 * @author kirk
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BussinessLitigationSourceBasicEntity对象", description="诉源表 测试读取excel")
public class BussinessLitigationSourceBasicEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "uuid",type = IdType.ASSIGN_UUID)
    private String uuid;

    @ApiModelProperty(value = "调解委员会")
    private String mediationCommittee;

    @ApiModelProperty(value = "受理员")
    private String acceptor;

    @ApiModelProperty(value = "调解工作室")
    private String mediationStudio;

    @ApiModelProperty(value = "案件编码")
    private String caseCode;

    @ApiModelProperty(value = "来源渠道")
    private String sourceChannel;

    @ApiModelProperty(value = "协办调委会")
    private String coOrganizingCommission;

    @ApiModelProperty(value = "受理时间")
    private String acceptanceTime;

    @ApiModelProperty(value = "案件来源")
    private Integer sourceOfCase;

    @ApiModelProperty(value = "案件难度级别")
    private String caseDifficultyLevel;

    @ApiModelProperty(value = "纠纷类别")
    private String typeOfDispute;

    @ApiModelProperty(value = "行政划分")
    private String administrativeDivision;

    @ApiModelProperty(value = "纠纷发生地")
    private String placeOfDispute;

    @ApiModelProperty(value = "当事人")
    private String party;

    @ApiModelProperty(value = "案件属性")
    private String theNatureOfTheCase;

    @ApiModelProperty(value = "涉及特殊群体情况")
    private String casesInvolvingSpecialGroups;

    @ApiModelProperty(value = "纠纷简要情况")
    private String briefInformation;

    @ApiModelProperty(value = "有无死亡")
    private String deathOrNot;

    @ApiModelProperty(value = "申请回避")
    private String applicationForWithdrawal;

    @ApiModelProperty(value = "案件预测")
    private String casePrediction;

    @ApiModelProperty(value = "申请金额")
    private String applicationAmount;

    @ApiModelProperty(value = "调解时间")
    private String mediationTime;

    @ApiModelProperty(value = "调解书编号")
    private String mediationNo;

    @ApiModelProperty(value = "履行方式")
    private String modeOfPerformance;

    @ApiModelProperty(value = "调解结果")
    private Integer mediationResults;

    @ApiModelProperty(value = "调解协议金")
    private String mediationAgreementFee;

    @ApiModelProperty(value = "协议履行情况")
    private Integer agreementPerformance;

    @ApiModelProperty(value = "纠纷转化情况")
    private String disputeTransformation;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "主办调解员")
    private String hostMediator;

    @ApiModelProperty(value = "协办调解员")
    private String coMediator;

    @ApiModelProperty(value = "协议形式")
    private String formOfAgreement;

    @ApiModelProperty(value = "以奖代补评定")
    private String awardInsteadOfCompensation;

    @ApiModelProperty(value = "涉及农民工纠纷情况")
    private String disputesInvolvingMigrantWorkers;
}
