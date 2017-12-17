var FormValidation = function () {

    // basic validation
    var handleValidation1 = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form1 = $('#form_save');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);
        jQuery.validator.addMethod("isDigits", function(value, element) {       
            return this.optional(element) || /^\d+$/.test(value);       
       }, "只能输入0-9数字组合");  
        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
          
            rules: {
            	className: {
                    required: true,
                    maxlength:20
                },
                grade: {
                    required: true,
                    maxlength:20,
                    isDigits:true
                },
            	
                classNum: {
                    required: true,
                    maxlength:20,
                    isDigits:true
                },
                departmentList:{
                	required:true
                },
                majorList:{
                	required:true
                }
            },
            messages: {

            	className:{
                    required:"班级名字不能为空",
                    className:"请正确的输入班级名字"
                },
                
                grade: {
                    required: "年级不能为空",
                    grade:"请输入正确年级"
                },
                classNum: {
                    required: "班级人数不能为空",
                    classNum:"请输入阿拉伯数字"
                } ,
                departmentList:{
                	required:"专业不能为空"
                },
                majorList:{
                	required:"专业不能为空"
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

