var FormValidation = function () {

    // basic validation
    var handleValidation1 = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form1 = $('#tableForm');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);
        jQuery.validator.addMethod("isdate",function (value, element) {
            var matches = /(\d{4})[-](\d{2})[-](\d{2})/;
            return this.optional(element) || (matches.test(value));
        },"请输入格式为yyyy-MM-dd出生日期");
        jQuery.validator.addMethod("isMobile", function(value, element) {  
            var length = value.length;  
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  
            return this.optional(element) || (length == 11 && mobile.test(value));  
        }, "请正确填写您的手机号码");
        jQuery.validator.addMethod("checkIdCardNo", function(value, element) {  
        	  return this.optional(element) || isIdCardNo(value);
        },"请正确输入您的身份证号码");
        jQuery.validator.addMethod("isZipCode", function(value, element) {   
            var tel = /^[0-9]{6}$/;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的邮政编码");
        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
          
            rules: {
            	name: {
                    required: true,
                    maxlength:20,
                },
                schoolNumber: {
                    required: true,
                    maxlength:20,
                },
                gender:{
                	required:true,
                },
            	photo: {
                    extension: "gif|png|jpg|jpeg|bmp",
                },
                nation: {
                    required: true,
                    maxlength:20,
                },
                political_status: {
                    required: true,
                    maxlength:20,
                },
            	ID: {
                    required: true,
                    minlength:18,
                    checkIdCardNo:true,
                },
                birthday: {
                    required: true,
                    date:true,
             	    isdate:true,
                },
            	native: {
                    required: true,
                    maxlength:20,
                },
                entranceTime: {
                    required: true,
                    date:true,
             	    isdate:true,
                },
                education: {
                    required: true,
                    maxlength:20,
                },
                address: {
                    required: true,
                    maxlength:100,
                },
                homePhone: {
                    required: true,
                    minlength : 11,  
                    isMobile : true,
                },
                postalcode: {
                    required: true,
                    maxlength:6,
                    isZipCode:true,
                },
                
                selfPhone: {
                    required: true,
                    minlength : 11,  
                    isMobile : true,
                },
                email: {
                    required: true,
                    email:true,
                }
                
            },
            messages: {

            	realname:{
                    required:"名字不能为空",
                    realname:"请正确的输入名字",
                },
                
                schoolNumber: {
                    required: "学号不能为空",
                    schoolNumber:"请输入正确的学号",
                },
                gender:{
                	required:"性别不能为空",
                },
            	photo: {
                    extension: "请上传后缀名为gif|png|jpg|jpeg|bmp的照片",
                },
                nation: {
                    required: "民族不能为空",
                    nation:"请正确输入名族全名",
                },
                political_status: {
                    required: "政治面貌不能为空",
                    political_status:"请输入正确的政治面貌",
                },
            	ID: {
                    required: "身份证号码不能为空",
                    ID:"请正确输入身份证号",
                },
                birthday: {
                    required: "出生日期不能为空",
                    birthday:"请输入正确的出生日期",
                },
            	native: {
                    required: "籍贯不能为空",
                    native:"请输入正确的籍贯格式为：xxx省xx市",
                },
                entranceTime: {
                    required: "入学年份不能为空",
                    entrance_time:"请输入正确的入学年份",
                },
                education: {
                    required: "学历不能为空",
                    education:"请输入正确的学历",
                },
                address: {
                    required: "家庭住址不能为空",
                    address:"请输入正确的家庭地址",
                },
                homePhone: {
                    required: "家庭联系方式不能为空",
                    home_phone:"请输入正确的手机联系方式",
                },
                postalcode: {
                    required: "邮政编码不能为空",
                    postalcode:"请输入正确的邮政编码",
                },
                
                self_phone: {
                    required: "个人联系方式不能为空",
                    self_phone:"请正确输入个人的联系方式",
                },
                email: {
                    required: "邮箱地址不能为空",
                    email:"请输入正确邮箱地址",
                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control member.group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error').removeClass('has-success'); // set error class to the control member.group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error').removeClass('has-success'); // set success class to the control member.group
            },

            submitHandler: function (form) {
                success1.show();
                error1.hide();
                form[0].submit(); // submit the form
            }
        });


    };
    // basic validation
    



    return {
        //main function to initiate the module
        init: function () {
            handleValidation1();
            handleValidation2();
        }

    };

}();

jQuery(document).ready(function() {
    FormValidation.init();
});

function isIdCardNo(num){ 

  var len = num.length, re,B; 
  if (len == 15) 
  re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
  else if (len == 18) 
  re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
  else {
        //alert("输入的数字位数不对。"); 
        return false;
    } 
     var a = num.match(re); 
     if (a != null) 
    {
      if (len==15) 
    {
      var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
      B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
     }
    else 
   {
     var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
     B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
   }
   if (!B) {
        //alert("输入的身份证号 "+ a[0] +" 里出生日期不对。"); 
        return false;
     }
  }
    if(!re.test(num)){
        //alert("身份证最后一位只能是数字和字母。");
        return false;
    }
    return true; 
} 