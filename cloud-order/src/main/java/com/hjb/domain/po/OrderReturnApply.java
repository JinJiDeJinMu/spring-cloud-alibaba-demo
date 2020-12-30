package com.hjb.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * �����˻�����
 * </p>
 *
 * @author jinmu
 * @since 2020-12-30
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@TableName("hu_order_return_apply")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * order_id
     */
      private Long orderId;

      /**
     * �˻���Ʒid
     */
      private Long skuId;

      /**
     * �������
     */
      private String orderSn;

      /**
     * ����ʱ��
     */
      private Date createTime;

      /**
     * ��Ա�û���
     */
      private String memberUsername;

      /**
     * �˿���
     */
      private BigDecimal returnAmount;

      /**
     * �˻�������
     */
      private String returnName;

      /**
     * �˻��˵绰
     */
      private String returnPhone;

      /**
     * ����״̬[0->�������1->�˻��У�2->����ɣ�3->�Ѿܾ�]
     */
      private Boolean status;

      /**
     * ����ʱ��
     */
      private Date handleTime;

      /**
     * ��ƷͼƬ
     */
      private String skuImg;

      /**
     * ��Ʒ����
     */
      private String skuName;

      /**
     * ��ƷƷ��
     */
      private String skuBrand;

      /**
     * ��Ʒ��������(JSON)
     */
      private String skuAttrsVals;

      /**
     * �˻�����
     */
      private Integer skuCount;

      /**
     * ��Ʒ����
     */
      private BigDecimal skuPrice;

      /**
     * ��Ʒʵ��֧������
     */
      private BigDecimal skuRealPrice;

      /**
     * ԭ��
     */
      private String reason;

      /**
     * ����
     */
      private String description��;

      /**
     * ƾ֤ͼƬ���Զ��Ÿ��
     */
      private String descPics;

      /**
     * �����ע
     */
      private String handleNote;

      /**
     * ������Ա
     */
      private String handleMan;

      /**
     * �ջ���
     */
      private String receiveMan;

      /**
     * �ջ�ʱ��
     */
      private Date receiveTime;

      /**
     * �ջ���ע
     */
      private String receiveNote;

      /**
     * �ջ��绰
     */
      private String receivePhone;

      /**
     * ��˾�ջ���ַ
     */
      private String companyAddress;


}
