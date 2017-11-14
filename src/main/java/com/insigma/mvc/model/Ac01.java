package com.insigma.mvc.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class Ac01  extends PageInfo implements java.io.Serializable  {
    
	/**
	 * ��JSR303���Ѿ������Constraint���£�
		�ռ��
		
		@Null       ��֤�����Ƿ�Ϊnull
		
		@NotNull      ��֤�����Ƿ�Ϊnull, �޷���쳤��Ϊ0���ַ���
		
		@NotBlank ���Լ���ַ����ǲ���Null���б�Trim�ĳ����Ƿ����0,ֻ���ַ���,�һ�ȥ��ǰ��ո�.
		
		@NotNull ���Լ��Ԫ���Ƿ�ΪNULL������EMPTY.
		
		Booelan���
		
		@AssertTrue     ��֤ Boolean �����Ƿ�Ϊ true  
		
		@AssertFalse    ��֤ Boolean �����Ƿ�Ϊ false  
		 
		
		���ȼ��
		
		@Size(min=, max=) ��֤����Array,Collection,Map,String�������Ƿ��ڸ����ķ�Χ֮��  
		
		@Length(min=, max=) 
		��֤�ַ����ĳ����Ƿ��ڸ����ķ�Χ֮�ڣ���������
		
		���ڼ��
		
		@Past        ��֤ Date �� Calendar �����Ƿ��ڵ�ǰʱ��֮ǰ  
		
		@Future     ��֤ Date �� Calendar �����Ƿ��ڵ�ǰʱ��֮��  
		
		@Pattern    ��֤ String �����Ƿ����������ʽ�Ĺ���
		 
		
		��ֵ��飺����ʹ����Stirng,Integer���ͣ�������ʹ����int�����ϣ���Ϊ��ֵΪ����ʱ�޷�ת��Ϊint��������ת��ΪStirngΪ"",IntegerΪnull
		
		@Min            ��֤ Number �� String �����Ƿ�����ָ����ֵ  
		
		@Max            ��֤ Number �� String �����Ƿ�С����ָ����ֵ  
		
		@DecimalMax ����ע��ֵ���벻����Լ����ָ�������ֵ. ���Լ���Ĳ�����һ��ͨ��BigDecimal��������ֵ���ַ�����ʾ.С�����ھ���
		
		@DecimalMin ����ע��ֵ���벻С��Լ����ָ������Сֵ. ���Լ���Ĳ�����һ��ͨ��BigDecimal�������Сֵ���ַ�����ʾ.С�����ھ���
		
		@Digits     ��֤ Number �� String �Ĺ����Ƿ�Ϸ�  
		
		@Digits(integer=,fraction=) ��֤�ַ����Ƿ��Ƿ���ָ����ʽ�����֣�intergerָ���������ȣ�fractionָ��С�����ȡ�
		 
		
		@Range(min=, max=) Checks whether the annotated value lies between (inclusive) the specified minimum and maximum.
		
		@Range(min=10000,max=50000,message="range.bean.wage")
		private BigDecimal wage;
		
		@Valid�ݹ�ĶԹ����������У��, ������������Ǹ����ϻ�������,��ô�����е�Ԫ�ؽ��еݹ�У��,�����һ��map,������е�ֵ���ֽ���У��.(�Ƿ���еݹ���֤)
		
		@CreditCardNumber���ÿ���֤
		
		@Email ��֤�Ƿ����ʼ���ַ�����Ϊnull,��������֤����ͨ����֤��
		
		@ScriptAssert(lang= ,script=, alias=)
		
		@URL(protocol=,host=, port=,regexp=, flags=)
	 */
	
	private String aac001;
    
	@NotNull(message="��������Ϊ��")
	@Length (min=2, max=5, message="��������ֻ����3-5֮��")
    private String aac003;

	@NotNull(message="���֤���벻��Ϊ��")
    private String aac002;

	@NotNull(message="�Ա���Ϊ��")
    private String aac004;

	@NotNull(message="���岻��Ϊ��")
    private String aac005;

	@NotNull(message="�������ڲ���Ϊ��")
	//��ǰ�����˴������ڸ�ʽת����
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aac006;
	
	private String aac006_string;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aac006_begin;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aac006_end;
    
    private String aae009;

    @NotNull(message="����״������Ϊ��")
	private String aac033;

    @NotNull(message="����״������Ϊ��")
    private String aac017;

    @NotNull(message="������ò����Ϊ��")
    private String aac024;

    @NotNull(message="ѧ������Ϊ��")
    private String aac011;
    
    private String [] a_aac011;
    

   

	@NotNull(message="��ϵ�绰����Ϊ��")
    private String aae006;

    private String aac067;

    @NotNull(message="���䲻��Ϊ��")
    @Email (message="�ʼ���ʽ����")
    private String aae015;

    private String aac007;
    
    private String aac007_name;
    

    public String getAac006_string() {
		return aac006_string;
	}

	public void setAac006_string(String aac006_string) {
		this.aac006_string = aac006_string;
	}

	private String aac027;

    private String adc100;

    private String aaf011;
    
    private String aae011;
    
    private String aae010;


	private Date aae036;

    private Long aaz002;

    private String aae100;

    private String eae052;

    private String aae200;

    private Date aae202;

    private String aae201;

    private String aae203;

    private String aaa148;

    private String adc300;

    private String aac127;

    private String aac032;

    private Integer aac034;

    private Integer aac035;
    
    private String aab301;
    
    private String aab800;
    
    private String aab801;
    
    private String aab802;
    
    
    
    public String getAac007_name() {
		return aac007_name;
	}

	public void setAac007_name(String aac007_name) {
		this.aac007_name = aac007_name;
	}

	public String getAab800() {
		return aab800;
	}

	public void setAab800(String aab800) {
		this.aab800 = aab800;
	}

	public String getAab801() {
		return aab801;
	}

	public void setAab801(String aab801) {
		this.aab801 = aab801;
	}

	public String getAab802() {
		return aab802;
	}

	public void setAab802(String aab802) {
		this.aab802 = aab802;
	}

	public String[] getA_aac011() {
		return a_aac011;
	}

	public void setA_aac011(String[] a_aac011) {
		this.a_aac011 = a_aac011;
	}

    public String getAab301() {
		return aab301;
	}

	public void setAab301(String aab301) {
		this.aab301 = aab301;
	}

	public String getSelectnodes() {
		return selectnodes;
	}

	public void setSelectnodes(String selectnodes) {
		this.selectnodes = selectnodes;
	}

	private String aac036;

    private String aae013;
    
    private String selectnodes;
    
    private String bus_uuid;
    
    
    


	public String getBus_uuid() {
		return bus_uuid;
	}

	public void setBus_uuid(String bus_uuid) {
		this.bus_uuid = bus_uuid;
	}

	public String getAae010() {
		return aae010;
	}

	public void setAae010(String aae010) {
		this.aae010 = aae010;
	}
	
    public Date getAac006_begin() {
		return aac006_begin;
	}

	public void setAac006_begin(Date aac006_begin) {
		this.aac006_begin = aac006_begin;
	}

	public Date getAac006_end() {
		return aac006_end;
	}

	public void setAac006_end(Date aac006_end) {
		this.aac006_end = aac006_end;
	}

	public String getAae009() {
  		return aae009;
  	}

  	public void setAae009(String aae009) {
  		this.aae009 = aae009;
  	}

    public String getAac001() {
        return aac001;
    }

    public void setAac001(String aac001) {
        this.aac001 = aac001 == null ? null : aac001.trim();
    }

    public String getAac003() {
        return aac003;
    }

    public void setAac003(String aac003) {
        this.aac003 = aac003 == null ? null : aac003.trim();
    }

    public String getAac002() {
        return aac002;
    }

    public void setAac002(String aac002) {
        this.aac002 = aac002 == null ? null : aac002.trim();
    }

    public String getAac004() {
        return aac004;
    }

    public void setAac004(String aac004) {
        this.aac004 = aac004 == null ? null : aac004.trim();
    }

    public String getAac005() {
        return aac005;
    }

    public void setAac005(String aac005) {
        this.aac005 = aac005 == null ? null : aac005.trim();
    }

    public Date getAac006() {
        return aac006;
    }

    public void setAac006(Date aac006) {
        this.aac006 = aac006;
    }

    public String getAac033() {
        return aac033;
    }

    public void setAac033(String aac033) {
        this.aac033 = aac033 == null ? null : aac033.trim();
    }

    public String getAac017() {
        return aac017;
    }

    public void setAac017(String aac017) {
        this.aac017 = aac017 == null ? null : aac017.trim();
    }

    public String getAac024() {
        return aac024;
    }

    public void setAac024(String aac024) {
        this.aac024 = aac024 == null ? null : aac024.trim();
    }

    public String getAac011() {
        return aac011;
    }

    public void setAac011(String aac011) {
        this.aac011 = aac011 == null ? null : aac011.trim();
    }

    public String getAae006() {
        return aae006;
    }

    public void setAae006(String aae006) {
        this.aae006 = aae006 == null ? null : aae006.trim();
    }

    public String getAac067() {
        return aac067;
    }

    public void setAac067(String aac067) {
        this.aac067 = aac067 == null ? null : aac067.trim();
    }

    public String getAae015() {
        return aae015;
    }

    public void setAae015(String aae015) {
        this.aae015 = aae015 == null ? null : aae015.trim();
    }

    public String getAac007() {
        return aac007;
    }

    public void setAac007(String aac007) {
        this.aac007 = aac007 == null ? null : aac007.trim();
    }

    public String getAac027() {
        return aac027;
    }

    public void setAac027(String aac027) {
        this.aac027 = aac027 == null ? null : aac027.trim();
    }

    public String getAdc100() {
        return adc100;
    }

    public void setAdc100(String adc100) {
        this.adc100 = adc100 == null ? null : adc100.trim();
    }

    public String getAaf011() {
        return aaf011;
    }

    public void setAaf011(String aaf011) {
        this.aaf011 = aaf011 == null ? null : aaf011.trim();
    }

    public String getAae011() {
        return aae011;
    }

    public void setAae011(String aae011) {
        this.aae011 = aae011 == null ? null : aae011.trim();
    }

    public Date getAae036() {
        return aae036;
    }

    public void setAae036(Date aae036) {
        this.aae036 = aae036;
    }

    public Long getAaz002() {
        return aaz002;
    }

    public void setAaz002(Long aaz002) {
        this.aaz002 = aaz002;
    }

    public String getAae100() {
        return aae100;
    }

    public void setAae100(String aae100) {
        this.aae100 = aae100 == null ? null : aae100.trim();
    }

    public String getEae052() {
        return eae052;
    }

    public void setEae052(String eae052) {
        this.eae052 = eae052 == null ? null : eae052.trim();
    }

    public String getAae200() {
        return aae200;
    }

    public void setAae200(String aae200) {
        this.aae200 = aae200 == null ? null : aae200.trim();
    }

    public Date getAae202() {
        return aae202;
    }

    public void setAae202(Date aae202) {
        this.aae202 = aae202;
    }

    public String getAae201() {
        return aae201;
    }

    public void setAae201(String aae201) {
        this.aae201 = aae201 == null ? null : aae201.trim();
    }

    public String getAae203() {
        return aae203;
    }

    public void setAae203(String aae203) {
        this.aae203 = aae203 == null ? null : aae203.trim();
    }

    public String getAaa148() {
        return aaa148;
    }

    public void setAaa148(String aaa148) {
        this.aaa148 = aaa148 == null ? null : aaa148.trim();
    }

    public String getAdc300() {
        return adc300;
    }

    public void setAdc300(String adc300) {
        this.adc300 = adc300 == null ? null : adc300.trim();
    }

    public String getAac127() {
        return aac127;
    }

    public void setAac127(String aac127) {
        this.aac127 = aac127 == null ? null : aac127.trim();
    }

    public String getAac032() {
        return aac032;
    }

    public void setAac032(String aac032) {
        this.aac032 = aac032 == null ? null : aac032.trim();
    }

    public Integer getAac034() {
        return aac034;
    }

    public void setAac034(Integer aac034) {
        this.aac034 = aac034;
    }

    public Integer getAac035() {
        return aac035;
    }

    public void setAac035(Integer aac035) {
        this.aac035 = aac035;
    }

    public String getAac036() {
        return aac036;
    }

    public void setAac036(String aac036) {
        this.aac036 = aac036 == null ? null : aac036.trim();
    }

    public String getAae013() {
        return aae013;
    }

    public void setAae013(String aae013) {
        this.aae013 = aae013 == null ? null : aae013.trim();
    }
    
}