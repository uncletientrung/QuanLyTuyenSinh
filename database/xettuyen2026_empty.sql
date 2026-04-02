-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 02, 2026 lúc 11:09 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `xettuyen2026_empty`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctquyen`
--

CREATE TABLE `ctquyen` (
  `maphanquyen` int(11) NOT NULL,
  `machucnang` int(11) NOT NULL,
  `hanhdong` varchar(255) NOT NULL,
  `trangthai` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmucchucnang`
--

CREATE TABLE `danhmucchucnang` (
  `machucnang` int(11) NOT NULL,
  `tenchucnang` varchar(255) NOT NULL,
  `trangthai` tinyint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phanquyen`
--

CREATE TABLE `phanquyen` (
  `maphanquyen` int(11) NOT NULL,
  `tenphanquyen` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trangthai` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `matk` int(11) NOT NULL,
  `tendangnhap` varchar(255) NOT NULL,
  `matkhau` varchar(255) NOT NULL,
  `maphanquyen` int(11) NOT NULL,
  `trangthai` tinyint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`matk`, `tendangnhap`, `matkhau`, `maphanquyen`, `trangthai`) VALUES
(1, 'admin', '123', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_bangquydoi`
--

CREATE TABLE `xt_bangquydoi` (
  `idqd` int(11) NOT NULL,
  `d_phuongthuc` varchar(45) DEFAULT NULL COMMENT 'phương thức xét tuyển',
  `d_tohop` varchar(45) DEFAULT NULL COMMENT 'tổ hợp xét tuyển',
  `d_mon` varchar(45) DEFAULT NULL COMMENT 'Ví dụ: TO, DI, N1',
  `d_diema` decimal(6,2) DEFAULT NULL COMMENT 'mốc điểm quy đổi (Khoảng điểm bắt đầu)',
  `d_diemb` decimal(6,2) DEFAULT NULL COMMENT 'mốc điểm quy đổi',
  `d_diemc` decimal(6,2) DEFAULT NULL COMMENT 'mốc điểm quy đổi',
  `d_diemd` decimal(6,2) DEFAULT NULL COMMENT 'mốc điểm quy đổi (Khoảng điểm kết thúc)',
  `d_maquydoi` varchar(45) DEFAULT NULL COMMENT 'mã của dòng quy đổi để phân biệt từng quy tắc quy đổi',
  `d_phanvi` varchar(45) DEFAULT NULL COMMENT 'Điểm này ở mức cao hơn khoảng bao nhiêu % thí sinh'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_bangquydoi`
--

INSERT INTO `xt_bangquydoi` (`idqd`, `d_phuongthuc`, `d_tohop`, `d_mon`, `d_diema`, `d_diemb`, `d_diemc`, `d_diemd`, `d_maquydoi`, `d_phanvi`) VALUES
(1, 'THPT', 'A00', 'TO', 8.00, 8.50, 9.00, 9.50, 'QD_A00_TO_01', 'P80'),
(2, 'THPT', 'A01', 'N1', 7.50, 8.00, 8.50, 9.00, 'QD_A01_N1_01', 'P75'),
(3, 'THPT', 'D01', 'N1', 8.00, 8.50, 9.00, 9.50, 'QD_D01_N1_01', 'P85'),
(4, 'THPT', 'C00', 'VA', 7.00, 8.00, 9.00, 9.50, 'QD_C00_VA_01', 'P70'),
(5, 'DGNL', 'DGNL', 'NL1', 600.00, 700.00, 800.00, 900.00, 'QD_DGNL_NL1_01', 'P90');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_diemcongxetuyen`
--

CREATE TABLE `xt_diemcongxetuyen` (
  `iddiemcong` int(10) UNSIGNED NOT NULL,
  `ts_cccd` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CCCD của thí sinh',
  `manganh` varchar(20) DEFAULT '0.00',
  `matohop` varchar(10) DEFAULT '0.00',
  `phuongthuc` varchar(45) DEFAULT NULL,
  `diemCC` decimal(6,2) DEFAULT NULL COMMENT 'Điểm cộng từ chứng chỉ',
  `diemUtxt` decimal(6,2) DEFAULT NULL COMMENT 'Điểm ưu tiên xét tuyển = Điểm khu vực + điểm ưu tiên đối tượng',
  `diemTong` decimal(6,2) DEFAULT 0.00 COMMENT 'điểm tổ hợp gốc + điểm cộng chứng chỉ + điểm ưu tiên',
  `ghichu` text DEFAULT NULL,
  `dc_keys` varchar(45) NOT NULL COMMENT 'Khóa ghép để định danh duy nhất một bản ghi\r\n\r\nví dụ: CCCD_MANGANH_MATOHOP_PHUONGTHUC'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_diemcongxetuyen`
--

INSERT INTO `xt_diemcongxetuyen` (`iddiemcong`, `ts_cccd`, `manganh`, `matohop`, `phuongthuc`, `diemCC`, `diemUtxt`, `diemTong`, `ghichu`, `dc_keys`) VALUES
(1, '079201000001', '7480201', 'A00', 'THPT', 0.50, 0.75, 25.50, 'Có chứng chỉ và ưu tiên khu vực', '079201000001_7480201_A00_THPT'),
(2, '079201000002', '7340101', 'D01', 'THPT', 1.00, 0.50, 26.30, 'Môn tiếng Anh nổi bật', '079201000002_7340101_D01_THPT'),
(3, '079201000003', '7480201', 'A01', 'THPT', 0.75, 0.25, 26.55, 'Ưu tiên đối tượng nhẹ', '079201000003_7480201_A01_THPT'),
(4, '079201000004', '7140201', 'C00', 'THPT', 0.25, 1.00, 27.50, 'Khu vực 1', '079201000004_7140201_C00_THPT'),
(5, '079201000005', '7520201', 'A00', 'THPT', 0.00, 0.25, 22.70, 'Không có chứng chỉ cộng thêm', '079201000005_7520201_A00_THPT'),
(6, '079201000006', '7220201', 'D01', 'THPT', 1.50, 0.50, 27.55, 'Tiếng Anh tốt', '079201000006_7220201_D01_THPT');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_diemthixettuyen`
--

CREATE TABLE `xt_diemthixettuyen` (
  `iddiemthi` int(11) NOT NULL,
  `cccd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sobaodanh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `d_phuongthuc` varchar(10) DEFAULT NULL,
  `TO` decimal(8,2) DEFAULT 0.00,
  `LI` decimal(8,2) DEFAULT 0.00,
  `HO` decimal(8,2) DEFAULT 0.00,
  `SI` decimal(8,2) DEFAULT 0.00,
  `SU` decimal(8,2) DEFAULT 0.00,
  `DI` decimal(8,2) DEFAULT 0.00,
  `VA` decimal(8,2) DEFAULT 0.00,
  `N1_THI` decimal(8,2) DEFAULT NULL COMMENT 'Điểm thi gốc',
  `N1_CC` decimal(8,2) DEFAULT 0.00 COMMENT 'max(N1_Thi, N1_QD)',
  `CNCN` decimal(8,2) DEFAULT 0.00 COMMENT 'Công nghệ công nghiệp',
  `CNNN` decimal(8,2) DEFAULT 0.00 COMMENT 'Công nghệ nông nghiệp',
  `TI` decimal(8,2) DEFAULT 0.00,
  `KTPL` decimal(8,2) DEFAULT 0.00 COMMENT 'Kinh tế và pháp luật',
  `NL1` decimal(8,2) DEFAULT NULL COMMENT 'Điểm năng lực 1',
  `NK1` decimal(8,2) DEFAULT NULL COMMENT 'Điểm năng khiếu 1',
  `NK2` decimal(8,2) DEFAULT NULL COMMENT 'Điểm năng khiếu 2'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_diemthixettuyen`
--

INSERT INTO `xt_diemthixettuyen` (`iddiemthi`, `cccd`, `sobaodanh`, `d_phuongthuc`, `TO`, `LI`, `HO`, `SI`, `SU`, `DI`, `VA`, `N1_THI`, `N1_CC`, `CNCN`, `CNNN`, `TI`, `KTPL`, `NL1`, `NK1`, `NK2`) VALUES
(1, '079201000001', 'BD001', 'THPT', 8.50, 8.00, 7.75, 6.50, 5.50, 6.00, 7.00, 8.25, 8.25, 0.00, 0.00, 7.50, 6.50, 780.00, NULL, NULL),
(2, '079201000002', 'BD002', 'THPT', 7.80, 7.20, 7.00, 6.80, 7.40, 7.60, 8.10, 8.90, 8.90, 0.00, 0.00, 7.00, 7.20, 820.00, NULL, NULL),
(3, '079201000003', 'BD003', 'THPT', 9.00, 8.75, 8.50, 7.20, 6.00, 6.50, 7.25, 7.80, 7.80, 0.00, 0.00, 8.20, 6.80, 910.00, NULL, NULL),
(4, '079201000004', 'BD004', 'THPT', 6.80, 6.40, 6.20, 7.00, 8.60, 8.75, 8.90, 7.50, 7.50, 0.00, 0.00, 6.50, 8.10, 760.00, NULL, NULL),
(5, '079201000005', 'BD005', 'THPT', 7.25, 7.00, 8.20, 8.40, 5.80, 6.10, 6.70, 6.95, 6.95, 0.00, 0.00, 7.80, 6.00, 700.00, NULL, NULL),
(6, '079201000006', 'BD006', 'THPT', 8.10, 7.50, 7.40, 7.30, 8.20, 8.00, 8.60, 8.85, 8.85, 0.00, 0.00, 7.20, 8.30, 845.00, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_khuvuc`
--

CREATE TABLE `xt_khuvuc` (
  `idkhuvuc` int(11) NOT NULL,
  `makv` varchar(45) NOT NULL,
  `tenkhuvuc` varchar(100) NOT NULL,
  `diemuutien` decimal(8,2) NOT NULL,
  `trangthai` tinyint(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_khuvuc`
--

INSERT INTO `xt_khuvuc` (`idkhuvuc`, `makv`, `tenkhuvuc`, `diemuutien`, `trangthai`) VALUES
(1, 'KV1', 'Khu vực 1', 0.75, 1),
(2, 'KV2', 'Khu vực 2', 0.25, 1),
(3, 'KV2NT', 'KV2 nông thôn', 0.50, 1),
(4, 'KV3', 'Khu vực 3', 0.00, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_nganh`
--

CREATE TABLE `xt_nganh` (
  `idnganh` int(11) NOT NULL,
  `manganh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tennganh` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `n_tohopgoc` varchar(3) DEFAULT NULL,
  `n_chitieu` int(11) NOT NULL DEFAULT 0,
  `n_diemsan` decimal(10,2) DEFAULT NULL,
  `n_diemtrungtuyen` decimal(10,2) DEFAULT NULL,
  `n_tuyenthang` varchar(1) DEFAULT NULL COMMENT '1 là có, 0 là không',
  `n_dgnl` varchar(1) DEFAULT NULL COMMENT '1 là có, 0 là không',
  `n_thpt` varchar(1) DEFAULT NULL COMMENT '1 là có, 0 là không',
  `n_vsat` varchar(1) DEFAULT NULL COMMENT '1 là có, 0 là không',
  `sl_xtt` int(11) DEFAULT NULL,
  `sl_dgnl` int(11) DEFAULT NULL,
  `sl_vsat` int(11) DEFAULT NULL,
  `sl_thpt` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_nganh`
--

INSERT INTO `xt_nganh` (`idnganh`, `manganh`, `tennganh`, `n_tohopgoc`, `n_chitieu`, `n_diemsan`, `n_diemtrungtuyen`, `n_tuyenthang`, `n_dgnl`, `n_thpt`, `n_vsat`, `sl_xtt`, `sl_dgnl`, `sl_vsat`, `sl_thpt`) VALUES
(1, '7480201', 'Công nghệ thông tin', 'A00', 120, 18.00, 24.50, '1', '1', '1', '0', 5, 35, 0, '80'),
(2, '7340101', 'Quản trị kinh doanh', 'D01', 100, 17.00, 23.00, '1', '1', '1', '0', 5, 30, 0, '65'),
(3, '7220201', 'Ngôn ngữ Anh', 'D01', 90, 18.00, 24.00, '1', '0', '1', '0', 10, 0, 0, '80'),
(4, '7520201', 'Kỹ thuật điện', 'A00', 80, 17.50, 22.50, '0', '1', '1', '0', 0, 25, 0, '55'),
(5, '7140201', 'Giáo dục Tiểu học', 'C00', 70, 19.00, 25.00, '1', '0', '1', '0', 5, 0, 0, '65');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_nganh_tohop`
--

CREATE TABLE `xt_nganh_tohop` (
  `id` int(11) NOT NULL,
  `manganh` varchar(45) NOT NULL,
  `matohop` varchar(45) NOT NULL,
  `th_mon1` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `hsmon1` tinyint(4) DEFAULT NULL,
  `th_mon2` varchar(10) DEFAULT NULL,
  `hsmon2` tinyint(4) DEFAULT NULL,
  `th_mon3` varchar(10) DEFAULT NULL,
  `hsmon3` tinyint(4) DEFAULT NULL,
  `tb_keys` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'manganh_matohop',
  `N1` tinyint(1) DEFAULT NULL,
  `TO` tinyint(1) DEFAULT NULL,
  `LI` tinyint(1) DEFAULT NULL,
  `HO` tinyint(1) DEFAULT NULL,
  `SI` tinyint(1) DEFAULT NULL,
  `VA` tinyint(1) DEFAULT NULL,
  `SU` tinyint(1) DEFAULT NULL,
  `DI` tinyint(1) DEFAULT NULL,
  `TI` tinyint(1) DEFAULT NULL,
  `KHAC` tinyint(1) DEFAULT NULL,
  `KTPL` tinyint(1) DEFAULT NULL,
  `dolech` decimal(6,2) DEFAULT 0.00 COMMENT 'Nhập tay chứ không phải thiết kế hệ thống'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_nganh_tohop`
--

INSERT INTO `xt_nganh_tohop` (`id`, `manganh`, `matohop`, `th_mon1`, `hsmon1`, `th_mon2`, `hsmon2`, `th_mon3`, `hsmon3`, `tb_keys`, `N1`, `TO`, `LI`, `HO`, `SI`, `VA`, `SU`, `DI`, `TI`, `KHAC`, `KTPL`, `dolech`) VALUES
(1, '7480201', 'A00', 'TO', 2, 'LI', 1, 'HO', 1, '7480201_A00', 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0.00),
(2, '7480201', 'A01', 'TO', 2, 'LI', 1, 'N1', 1, '7480201_A01', 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0.25),
(3, '7480201', 'D01', 'TO', 2, 'VA', 1, 'N1', 1, '7480201_D01', 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.50),
(4, '7340101', 'A01', 'TO', 1, 'LI', 1, 'N1', 2, '7340101_A01', 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0.00),
(5, '7340101', 'D01', 'TO', 1, 'VA', 1, 'N1', 2, '7340101_D01', 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.00),
(6, '7340101', 'C00', 'VA', 2, 'SU', 1, 'DI', 1, '7340101_C00', 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0.50),
(7, '7220201', 'D01', 'TO', 1, 'VA', 1, 'N1', 2, '7220201_D01', 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.00),
(8, '7220201', 'A01', 'TO', 1, 'LI', 1, 'N1', 2, '7220201_A01', 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0.25),
(9, '7520201', 'A00', 'TO', 2, 'LI', 1, 'HO', 1, '7520201_A00', 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0.00),
(10, '7520201', 'A01', 'TO', 2, 'LI', 1, 'N1', 1, '7520201_A01', 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0.25),
(11, '7140201', 'C00', 'VA', 2, 'SU', 1, 'DI', 1, '7140201_C00', 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0.00),
(12, '7140201', 'D01', 'TO', 1, 'VA', 2, 'N1', 1, '7140201_D01', 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.30);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_nguyenvongxettuyen`
--

CREATE TABLE `xt_nguyenvongxettuyen` (
  `idnv` int(11) NOT NULL,
  `nn_cccd` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nv_manganh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nv_tt` int(11) NOT NULL COMMENT 'Nguyện vọng thứ tự',
  `diem_thxt` decimal(10,5) DEFAULT NULL COMMENT 'đã cộng điểm môn chính (điểm tổ hợp xét tuyển)',
  `diem_utqd` decimal(10,5) DEFAULT NULL COMMENT 'Điểm UTQD theo tổ họp sẽ khác nhau.',
  `diem_cong` decimal(6,2) DEFAULT NULL COMMENT 'Tong 3 mon chua tinh mon chinh + diem uu tien\\\\\\\\n',
  `diem_xettuyen` decimal(10,5) DEFAULT NULL COMMENT 'đã cộng điểm ưu tiên',
  `nv_ketqua` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nv_keys` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tt_phuongthuc` varchar(45) DEFAULT NULL COMMENT 'Phương thức xét tuyển',
  `tt_thm` varchar(45) DEFAULT NULL COMMENT 'Mã tổ hợp môn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_nguyenvongxettuyen`
--

INSERT INTO `xt_nguyenvongxettuyen` (`idnv`, `nn_cccd`, `nv_manganh`, `nv_tt`, `diem_thxt`, `diem_utqd`, `diem_cong`, `diem_xettuyen`, `nv_ketqua`, `nv_keys`, `tt_phuongthuc`, `tt_thm`) VALUES
(1, '079201000001', '7480201', 1, 24.25000, 0.75000, 0.50, 25.50000, 'Đang xét', '079201000001_7480201_1', 'THPT', 'A00'),
(2, '079201000001', '7520201', 2, 24.25000, 0.25000, 0.50, 25.00000, 'Đang xét', '079201000001_7520201_2', 'THPT', 'A01'),
(3, '079201000002', '7340101', 1, 24.80000, 0.50000, 1.00, 26.30000, 'Đang xét', '079201000002_7340101_1', 'THPT', 'D01'),
(4, '079201000002', '7220201', 2, 24.80000, 0.25000, 1.00, 26.05000, 'Đang xét', '079201000002_7220201_2', 'THPT', 'A01'),
(5, '079201000003', '7480201', 1, 25.55000, 0.25000, 0.75, 26.55000, 'Đang xét', '079201000003_7480201_1', 'THPT', 'A01'),
(6, '079201000003', '7520201', 2, 26.25000, 0.00000, 0.00, 26.25000, 'Đang xét', '079201000003_7520201_2', 'THPT', 'A00'),
(7, '079201000004', '7140201', 1, 26.25000, 1.00000, 0.25, 27.50000, 'Đang xét', '079201000004_7140201_1', 'THPT', 'C00'),
(8, '079201000004', '7340101', 2, 25.10000, 1.00000, 0.25, 26.35000, 'Đang xét', '079201000004_7340101_2', 'THPT', 'C00'),
(9, '079201000005', '7520201', 1, 22.45000, 0.25000, 0.00, 22.70000, 'Đang xét', '079201000005_7520201_1', 'THPT', 'A00'),
(10, '079201000005', '7480201', 2, 21.45000, 0.25000, 0.00, 21.70000, 'Đang xét', '079201000005_7480201_2', 'THPT', 'A00'),
(11, '079201000006', '7220201', 1, 25.55000, 0.50000, 1.50, 27.55000, 'Đang xét', '079201000006_7220201_1', 'THPT', 'D01'),
(12, '079201000006', '7340101', 2, 25.55000, 0.50000, 1.50, 27.55000, 'Đang xét', '079201000006_7340101_2', 'THPT', 'D01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_thisinhxettuyen25`
--

CREATE TABLE `xt_thisinhxettuyen25` (
  `idthisinh` int(11) NOT NULL,
  `cccd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sobaodanh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ho` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ten` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngay_sinh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dien_thoai` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `noi_sinh` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updated_at` date DEFAULT NULL COMMENT 'Chỉnh sửa lần cuối',
  `doi_tuong` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Đối tượng ưu tiên',
  `khu_vuc` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Khu vực ưu tiên: KV1\r\n\r\nKV2\r\n\r\nKV2-NT\r\n\r\nKV3'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_thisinhxettuyen25`
--

INSERT INTO `xt_thisinhxettuyen25` (`idthisinh`, `cccd`, `sobaodanh`, `ho`, `ten`, `ngay_sinh`, `dien_thoai`, `password`, `gioi_tinh`, `email`, `noi_sinh`, `updated_at`, `doi_tuong`, `khu_vuc`) VALUES
(1, '079201000001', 'BD001', 'Nguyễn Văn', 'An', '2008-01-15', '0901000001', '123456', 'Nam', 'an01@gmail.com', 'TP.HCM', '2026-03-10', '01', 'KV3'),
(2, '079201000002', 'BD002', 'Trần Thị', 'Bình', '2008-03-20', '0901000002', '123456', 'Nữ', 'binh02@gmail.com', 'Đồng Nai', '2026-03-10', '02', 'KV2'),
(3, '079201000003', 'BD003', 'Lê Hoàng', 'Cường', '2008-07-11', '0901000003', '123456', 'Nam', 'cuong03@gmail.com', 'Bình Dương', '2026-03-10', '00', 'KV2-NT'),
(4, '079201000004', 'BD004', 'Phạm Ngọc', 'Dung', '2008-09-09', '0901000004', '123456', 'Nữ', 'dung04@gmail.com', 'Long An', '2026-03-10', '01', 'KV1'),
(5, '079201000005', 'BD005', 'Võ Minh', 'Em', '2008-12-01', '0901000005', '123456', 'Nam', 'em05@gmail.com', 'Tiền Giang', '2026-03-10', '00', 'KV3'),
(6, '079201000006', 'BD006', 'Đặng Thu', 'Giang', '2008-04-18', '0901000006', '123456', 'Nữ', 'giang06@gmail.com', 'Bến Tre', '2026-03-10', '03', 'KV2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xt_tohop_monthi`
--

CREATE TABLE `xt_tohop_monthi` (
  `idtohop` int(11) NOT NULL,
  `matohop` varchar(45) NOT NULL,
  `mon1` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mon2` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mon3` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tentohop` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `xt_tohop_monthi`
--

INSERT INTO `xt_tohop_monthi` (`idtohop`, `matohop`, `mon1`, `mon2`, `mon3`, `tentohop`) VALUES
(1, 'A00', 'TO', 'LI', 'HO', 'Toán, Vật Lí, Hóa học'),
(2, 'A01', 'TO', 'LI', 'N1', 'Toán, Vật Lí, Tiếng Anh'),
(3, 'A02', 'TO', 'LI', 'SI', 'Toán, Vật Lí, Sinh học'),
(4, 'B00', 'TO', 'HO', 'SI', 'Toán, Hóa học, Sinh học'),
(5, 'C00', 'VA', 'SU', 'DI', 'Ngữ văn, Lịch sử, Địa lí'),
(6, 'C01', 'VA', 'TO', 'LI', 'Ngữ văn, Toán, Vật Lí'),
(7, 'C02', 'VA', 'TO', 'HO', 'Ngữ văn, Toán, Hóa học'),
(8, 'C03', 'VA', 'TO', 'SU', 'Ngữ văn, Toán, Lịch sử'),
(9, 'D01', 'TO', 'VA', 'N1', 'Toán, Ngữ văn, Tiếng Anh'),
(10, 'D07', 'TO', 'HO', 'N1', 'Toán, Hóa học, Tiếng Anh'),
(11, 'D08', 'TO', 'SI', 'N1', 'Toán, Sinh học, Tiếng Anh'),
(12, 'D09', 'TO', 'SU', 'N1', 'Toán, Lịch sử, Tiếng Anh'),
(13, 'D10', 'TO', 'DI', 'N1', 'Toán, Địa lí, Tiếng Anh'),
(14, 'D14', 'VA', 'SU', 'N1', 'Ngữ văn, Lịch sử, Tiếng Anh'),
(15, 'D15', 'VA', 'DI', 'N1', 'Ngữ văn, Địa lí, Tiếng Anh');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD PRIMARY KEY (`maphanquyen`,`machucnang`,`hanhdong`),
  ADD KEY `FK_ctquyen_danhmucchucnang` (`machucnang`);

--
-- Chỉ mục cho bảng `danhmucchucnang`
--
ALTER TABLE `danhmucchucnang`
  ADD PRIMARY KEY (`machucnang`);

--
-- Chỉ mục cho bảng `phanquyen`
--
ALTER TABLE `phanquyen`
  ADD PRIMARY KEY (`maphanquyen`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`matk`);

--
-- Chỉ mục cho bảng `xt_bangquydoi`
--
ALTER TABLE `xt_bangquydoi`
  ADD PRIMARY KEY (`idqd`),
  ADD UNIQUE KEY `d_maquydoi_UNIQUE` (`d_maquydoi`);

--
-- Chỉ mục cho bảng `xt_diemcongxetuyen`
--
ALTER TABLE `xt_diemcongxetuyen`
  ADD PRIMARY KEY (`iddiemcong`),
  ADD UNIQUE KEY `dc_keys_UNIQUE` (`dc_keys`);

--
-- Chỉ mục cho bảng `xt_diemthixettuyen`
--
ALTER TABLE `xt_diemthixettuyen`
  ADD PRIMARY KEY (`iddiemthi`),
  ADD UNIQUE KEY `cccd_UNIQUE` (`cccd`);

--
-- Chỉ mục cho bảng `xt_khuvuc`
--
ALTER TABLE `xt_khuvuc`
  ADD PRIMARY KEY (`idkhuvuc`);

--
-- Chỉ mục cho bảng `xt_nganh`
--
ALTER TABLE `xt_nganh`
  ADD PRIMARY KEY (`idnganh`);

--
-- Chỉ mục cho bảng `xt_nganh_tohop`
--
ALTER TABLE `xt_nganh_tohop`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `key_UNIQUE` (`tb_keys`);

--
-- Chỉ mục cho bảng `xt_nguyenvongxettuyen`
--
ALTER TABLE `xt_nguyenvongxettuyen`
  ADD PRIMARY KEY (`idnv`),
  ADD UNIQUE KEY `nv_keys_UNIQUE` (`nv_keys`);

--
-- Chỉ mục cho bảng `xt_thisinhxettuyen25`
--
ALTER TABLE `xt_thisinhxettuyen25`
  ADD PRIMARY KEY (`idthisinh`),
  ADD UNIQUE KEY `cccd_UNIQUE` (`cccd`);

--
-- Chỉ mục cho bảng `xt_tohop_monthi`
--
ALTER TABLE `xt_tohop_monthi`
  ADD PRIMARY KEY (`idtohop`),
  ADD UNIQUE KEY `matohop_UNIQUE` (`matohop`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhmucchucnang`
--
ALTER TABLE `danhmucchucnang`
  MODIFY `machucnang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `phanquyen`
--
ALTER TABLE `phanquyen`
  MODIFY `maphanquyen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `matk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `xt_bangquydoi`
--
ALTER TABLE `xt_bangquydoi`
  MODIFY `idqd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `xt_diemcongxetuyen`
--
ALTER TABLE `xt_diemcongxetuyen`
  MODIFY `iddiemcong` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `xt_diemthixettuyen`
--
ALTER TABLE `xt_diemthixettuyen`
  MODIFY `iddiemthi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `xt_khuvuc`
--
ALTER TABLE `xt_khuvuc`
  MODIFY `idkhuvuc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `xt_nganh`
--
ALTER TABLE `xt_nganh`
  MODIFY `idnganh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `xt_nganh_tohop`
--
ALTER TABLE `xt_nganh_tohop`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `xt_nguyenvongxettuyen`
--
ALTER TABLE `xt_nguyenvongxettuyen`
  MODIFY `idnv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `xt_thisinhxettuyen25`
--
ALTER TABLE `xt_thisinhxettuyen25`
  MODIFY `idthisinh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `xt_tohop_monthi`
--
ALTER TABLE `xt_tohop_monthi`
  MODIFY `idtohop` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD CONSTRAINT `FK_ctquyen_danhmucchucnang` FOREIGN KEY (`machucnang`) REFERENCES `danhmucchucnang` (`machucnang`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_ctquyen_phanquyen` FOREIGN KEY (`maphanquyen`) REFERENCES `phanquyen` (`maphanquyen`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
