Dashmix.onLoad((() => class {
    static initValidation() {
        Dashmix.helpers("jq-validation");

        jQuery(".js-validation-thpt").validate({
            ignore: [],
            rules: {
                "nganh": {
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
                    max: 10
                },
                "nguVan": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "vatLy": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "hoaHoc": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "sinhHoc": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "lichSu": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "diaLy": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "tinHoc": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "ktpl": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "cnCongNghiep": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                },
                "cnNongNghiep": {
                    required: true,
                    number: true,
                    min: 0,
                    max: 10
                }
            },
            messages: {
                "nganh": {
                    required: "Vui lòng chọn ngành"
                },
                "diemCong": {
                    required: "Vui lòng nhập điểm cộng",
                    number: "Điểm cộng phải là số",
                    min: "Điểm cộng tối thiểu là 0",
                    max: "Điểm cộng tối đa là 3.0"
                },
                "toan": {
                    required: "Vui lòng nhập điểm Toán (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "nguVan": {
                    required: "Vui lòng nhập điểm Ngữ văn (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "vatLy": {
                    required: "Vui lòng nhập điểm Vật lý (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "hoaHoc": {
                    required: "Vui lòng nhập điểm Hóa học (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "sinhHoc": {
                    required: "Vui lòng nhập điểm Sinh học (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "lichSu": {
                    required: "Vui lòng nhập điểm Lịch sử (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "diaLy": {
                    required: "Vui lòng nhập điểm Địa lý (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "tinHoc": {
                    required: "Vui lòng nhập điểm Tin học (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "ktpl": {
                    required: "Vui lòng nhập điểm GD Kinh tế Pháp luật (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "cnCongNghiep": {
                    required: "Vui lòng nhập điểm CN Công nghiệp (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                },
                "cnNongNghiep": {
                    required: "Vui lòng nhập điểm CN Nông nghiệp (nhập 0 nếu không thi)",
                    number: "Điểm phải là số",
                    min: "Điểm tối thiểu là 0",
                    max: "Điểm tối đa là 10"
                }
            },
            errorClass: 'invalid-feedback animated fadeIn',
            errorElement: 'div',
            errorPlacement: function (error, element) {
                jQuery(element).addClass('is-invalid');
                jQuery(element).closest('.mb-3, .mb-4').append(error);
            },
            highlight: function (element) {
                jQuery(element).addClass('is-invalid');
            },
            unhighlight: function (element) {
                jQuery(element).removeClass('is-invalid');
            }
        });

        // Hỗ trợ validate khi thay đổi select2 (nếu bạn dùng)
        jQuery(".js-select2").on("change", function () {
            jQuery(this).valid();
        });
    }

    static init() {
        this.initValidation();
    }
}.init()));