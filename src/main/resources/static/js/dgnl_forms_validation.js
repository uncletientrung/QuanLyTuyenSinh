/*!
 * dashmix - v5.5.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2022
 */
Dashmix.onLoad((() => class {
    static initValidation() {
        Dashmix.helpers("jq-validation");

        jQuery(".js-validation-dgnl").validate({
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
                "diem-thi-input": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 990
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
                "diem-thi-input": {
                    required: "Vui lòng nhập điểm thi",
                    number: "Điểm thi phải là số",
                    min: "Điểm thi tối thiểu là 0",
                    max: "Điểm thi tối đa là 990"
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