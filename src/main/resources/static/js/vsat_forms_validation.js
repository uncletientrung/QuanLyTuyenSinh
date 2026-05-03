/*!
 * dashmix - v5.5.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2022
 */
Dashmix.onLoad((() => class {
    static initValidation() {
        Dashmix.helpers("jq-validation");

        jQuery(".js-validation-vsat").validate({
            ignore: [],
            rules: {
                "nganh-select": {
                    required: true
                },
                "diem-cong-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 3
                },
                "diem-toan-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-van-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-ly-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-hoa-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-sinh-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-su-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-dia-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diem-anh-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                }
            },
            messages: {
                "nganh-select": {
                    required: "Vui lòng chọn ngành"
                },
                "diem-cong-input": {
                    required: "Vui lòng nhập điểm cộng",
                    number: "Điểm cộng phải là số",
                    min: "Điểm cộng tối thiểu là 0",
                    max: "Điểm cộng tối đa là 3"
                },
                "diem-toan-input": {
                    required: "Vui lòng nhập điểm Toán (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-van-input": {
                    required: "Vui lòng nhập điểm Ngữ văn (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-ly-input": {
                    required: "Vui lòng nhập điểm Vật lý (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-hoa-input": {
                    required: "Vui lòng nhập điểm Hoá học (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-sinh-input": {
                    required: "Vui lòng nhập điểm Sinh học (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-su-input": {
                    required: "Vui lòng nhập điểm Lịch sử (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-dia-input": {
                    required: "Vui lòng nhập điểm Địa lý (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diem-anh-input": {
                    required: "Vui lòng nhập điểm Tiếng Anh (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                }
            },
            errorClass: 'invalid-feedback animated fadeIn',
            errorElement: 'div',
            errorPlacement: function (error, element) {
                jQuery(element).addClass('is-invalid');
                jQuery(element).closest('.mb-4').append(error);
            },
            highlight: function (element) {
                jQuery(element).addClass('is-invalid');
            },
            unhighlight: function (element) {
                jQuery(element).removeClass('is-invalid');
            },
        });

        jQuery(".js-select2").on("change", (e => {
            jQuery(e.currentTarget).valid()
        }));
    }

    static init() {
        this.initValidation();
    }
}.init()));