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
                "Manganh": {
                    required: true
                },
                "diemCong": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 3
                },
                "toan": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "nguVan": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "vatLy": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "hoaHoc": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "sinhHoc": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "lichSu": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "diaLy": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                },
                "tiengAnh": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 150
                }
            },
            messages: {
                "Manganh": {
                    required: "Vui lòng chọn ngành"
                },
                "diemCong": {
                    required: "Vui lòng nhập điểm cộng",
                    number: "Điểm cộng phải là số",
                    min: "Điểm cộng tối thiểu là 0",
                    max: "Điểm cộng tối đa là 3"
                },
                "toan": {
                    required: "Vui lòng nhập điểm Toán (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "nguVan": {
                    required: "Vui lòng nhập điểm Ngữ văn (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "vatLy": {
                    required: "Vui lòng nhập điểm Vật lý (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "hoaHoc": {
                    required: "Vui lòng nhập điểm Hoá học (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "sinhHoc": {
                    required: "Vui lòng nhập điểm Sinh học (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "lichSu": {
                    required: "Vui lòng nhập điểm Lịch sử (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "diaLy": {
                    required: "Vui lòng nhập điểm Địa lý (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
                },
                "tiengAnh": {
                    required: "Vui lòng nhập điểm Tiếng Anh (hoặc 0)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 150"
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