package com.youjun.api.enums;

import com.youjun.common.api.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorEnum
 * @description error类型枚举类
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum implements IErrorCode {
    // 系统错误
    UNKNOWN("500","系统内部错误，请联系管理员"),
    NO_AUTH("403","没有权限，请联系管理员"),
    DUPLICATE_KEY("501","数据库中已存在该记录"),
    TOKEN_GENERATOR_ERROR("502","token生成失败"),
    NO_UUID("503","uuid为空"),
    SQL_ILLEGAL("504","sql非法"),

    //用户权限错误
    INVALID_TOKEN("1001","token不合法"),
    INVALID_USERNAME("1002","用户名已存在"),
    INVALID_USERNAME_NULL("1003","用户名不存在"),
    INVALID_PHONE_NULL("1004","用户不存在，请点击注册"),
    INVALID_CUST_CORP_INFO("1005","账户认证中"),
    INVALID_PHONE_INFO_EXITS("1006","手机号已注册"),
    INVALID_PASSWORD("1007","用户名或密码错误"),
    USERINFO_FREEZE("1008","账户已被冻结"),
    USERINFO_NOT_PASS("1008","账号未认证"),
    USERINFO_NO_PASS("1009","账号认证未通过"),

    //登录模块错误
    LOGIN_FAIL("10001","登录失败"),
    CAPTCHA_WRONG("10002","验证码错误"),
    USERNAME_OR_PASSWORD_WRONG("10003","用户名或密码错误"),
    PHONE_USER_NULL("10004","手机号不存在"),
    LOGIN_TIME_OUT("10005","登录超时"),
    PHONE_NOT_REGISTER("10006","该手机号未注册，请先注册"),
    CARTNO_EXIST("10007", "身份证号已存在"),

    // 请求错误
    REQUEST_ARGUMENTS_EMPTY("30001","请求参数不能为空"),
    REQUEST_ARGUMENTS_EXITES("30002","请求参数已存在"),
    REQUEST_ARGUMENTS_ERROR("30011","请求参数错误"),
    REQUEST_FILE_EMPTY("30003","文件为空"),
    REQUEST_FILE_ERROR("30004","上传文件失败"),
    REQUEST_LOGIN_CODE_EMPTY("30005","短信验证码不能为空"),
    REQUEST_LOGIN_PHONE_EMPTY("30006","手机号不能为空"),
    REQUEST_LOGIN_CODE_ERROR("30007","短信验证码错误"),
    REQUEST_SEND_CODE_ERROR("30008","短信发送失败"),
    REQUEST_SEND_PHONE_EMPTY("30009","手机号不能为空"),
    REQUEST_FILE_NOT_FOUND("30010","文件未找到"),
    REQUEST_PC_LOGIN_CODE_ERROR("30012","验证码错误"),
    REQUEST_DRIVER_EXITS("30013","司机信息已添加"),
    REQUEST_PHONE_EXITS("30014","该手机号已存在"),
    REQUEST_PRODUCT_EXITS("30015","该产品名称已存在"),

    // 数据库错误
    INSERT_DB_ERROR("40001", "数据库数据插入失败"),

    //APP热更新
    UPGRADE_VERSION_ERROR("150001", "版本错误"),
    APP_LATEST_VERSION_NOT_EXISTS("150001", "App最新版本不存在"),

    //配置信息
    SYSCONFIG_EXISTS("160001", "配置信息已存在"),

    /**
     * 一级宏观错误码
     * 客户端错误
     */
    CLIENT("A0001", "客户端错误"),
    /**
     * 一级宏观错误码
     * 调用第三方服务出错
     */
    THIRD("C0001", "调用第三方服务出错"),
    /**
     * 枚举类型错误
     */
    ENUM_NOT_FOUND("10001", "找不到该枚举"),
    PATH_NOT_FOUND("10002", "路径不存在，请检查路径"),

    /**
     * 二级宏观错误码
     * 客户端请求参数异常（A0100 - A0199）
     */
    CLIENT_PARAM("A0100", "客户端请求参数异常"),
    CLIENT_PARAM_REQUIRED_IS_NULL("A0101", "请求必填参数为空"),
    CLIENT_PARAM_INPUT_INVALID("A0102", "无效的用户输入"),
    CLIENT_PARAM_FORMAT_INVALID("A0103", "参数格式不匹配"),
    CLIENT_PARAM_JSON_PARSE_FAILED("A0104", "请求 JSON 解析失败"),
    CLIENT_PARAM_INCLUDE_ILLEGAL_WORDS("A0105", "包含违禁敏感词"),
    CLIENT_PARAM_VALUE_OUT_RANGE("A0106", "请求参数值超出允许的范围"),
    CLIENT_PARAM_PARAMS_IS_NULL("A0107","参数不足"),
    /** 新密码两次输入不一致 */
    NEW_PASSWORD_NOT_MATCH("A0023", "新密码两次输入不一致！"),
    /** 旧密码不正确 */
    OLD_PASSWORD_ERROR("A0022", "旧密码不正确！"),
    /** 新密码不能与旧密码相同 */
    PASSWORD_IS_EQUAL("A0021", "新密码不能与旧密码相同"),

    /**
     * 二级宏观错误码
     * 认证授权异常（A0200 - A0299）
     */
    CLIENT_AUTH("A0200", "认证授权异常"),
    CLIENT_AUTH_ACCESS_DENIED("A0201", "访问未授权"),
    CLIENT_AUTH_AUTHORIZING("A0202", "正在授权中"),
    CLIENT_AUTH_REJECTED("A0203", "用户授权申请被拒绝"),
    CLIENT_AUTH_EXPIRED("A0204", "授权已过期"),
    CLIENT_AUTH_NO_PERMISSION("A0205", "权限不足"),
    CLIENT_AUTH_REQUEST_INTERCEPTED("A0206", "用户访问被拦截"),
    CLIENT_AUTH_BLACKLIST("A0207", "黑名单用户"),
    CLIENT_AUTH_ACCOUNT_LOCKED("A0208", "账号被冻结"),
    CLIENT_AUTH_IP_INVALID("A0209", "非法 IP 地址"),
    CLIENT_AUTH_GATEWAY_ACCESS_DENIED("A0210", "网关访问受限"),
    CLIENT_AUTH_REGION_BLACKLIST("A0211", "地域黑名单"),
    CLIENT_AUTH_SERVICE_OVERDUE("A0212", "服务已欠费"),
    CLIENT_AUTH_SIGNATURE_INVALID("A0213", "用户签名异常"),
    CLIENT_AUTH_RSA_SIGNATURE_ERROR("A0214", "RSA 签名错误"),
    CLIENT_AUTH_CLIENT_INVALID("A0215", "无效的客户端"),
    CLIENT_AUTH_CLIENT_UNAUTHORIZED("A0216", "未经授权的客户端"),
    CLIENT_AUTH_GRANT_INVALID("A0217", "认证失败"),
    CLIENT_AUTH_SCOPE_INVALID("A0218", "无效的认证范围"),
    CLIENT_AUTH_TOKEN_INVALID("A0219", "无效的 token"),
    CLIENT_AUTH_REQUEST_INVALID("A0220", "无效的请求"),
    CLIENT_AUTH_REDIRECT_MISMATCH("A0221", "重定向 URL 不匹配"),
    CLIENT_AUTH_GRANT_TYPE_UNSUPPORTED("A0222", "不支持的认证类型"),
    CLIENT_AUTH_RESPONSE_TYPE_UNSUPPORTED("A0223", "不支持的响应类型"),
    CLIENT_AUTH_INTERCEPTED_FOR_PRIVACY("A0224", "因访问对象隐私设置被拦截"),

    /**
     * 二级宏观错误码
     * 用户角色权限异常（A0300 - A0399）
     */
    CLIENT_UIMS("A0300", "用户权限异常"),
    CLIENT_UIMS_USER_EXIST("A0301", "用户已存在"),
    CLIENT_UIMS_USER_NOT_EXIST("A0302", "系统中无此账号，请联系管理员"),
    CLIENT_UIMS_CHECK_USERNAME("A1342", "用户名重复"),
    CLIENT_UIMS_NOT_EXIST("A1343", "用户名不存在"),
    CLIENT_UIMS_CHECK_PHONE("A0332", "手机号重复"),
    CLIENT_UIMS_ROLE_EXIST("A0303", "角色已存在"),
    CLIENT_UIMS_ROLE_NOT_EXIST("A0304", "角色不存在或被冻结"),
    CLIENT_UIMS_PERMISSION_EXIST("A0305", "权限已存在"),
    CLIENT_UIMS_PERMISSION_NOT_EXIST("A0306", "权限不存在"),
    CLIENT_UIMS_ACCOUNT_LOCKED("A0307", "用户账户被冻结或删除"),
    CLIENT_UIMS_ACCOUNT_CANCELED("A0308", "用户账户已作废"),
    CLIENT_UIMS_USER_DELETED("A0309", "用户被删除，用户名不能重复"),
    CLIENT_UIMS_ROLE_DELETED("A0310", "角色被删除，角色名不能重复"),
    CLIENT_UIMS_ACCOUNT_INVALID("A0311", "用户账号格式错误"),
    CLIENT_UIMS_ROLE_CODE_EXIST("A0312", "角色编码已存在"),

    CLIENT_UIMS_PASSWORD_INVALID("A0320", "用户名或密码错误"),
    CLIENT_UIMS_CODE_NULL("A0342", "请输入动态验证码"),
    CLIENT_UIMS_CODE1_NULL("A0344", "请绑定动态验证码"),
    CLIENT_UIMS_CODE2_NULL("A0345", "您已经绑定过动态验证码了,请联系管理员重新绑定"),
    CLIENT_UIMS_CODE_INVALID("A0343", "动态验证码错误"),
    CLIENT_UIMS_PASSWORD_COUNT_EXCEED("A0321", "用户输入密码次数超限"),
    CLIENT_UIMS_ROLE_INVALID("A0322", "用户身份校验失败"),
    CLIENT_UIMS_THIRD_AUTH_INVALID("A0323", "用户未获得第三方登录授权"),
    CLIENT_UIMS_EXPIRED("A0330", "用户登录已过期"),
    CLIENT_UIMS_VALIDATE_CODE_INVALID("A0340", "验证码错误或已过期"),
    CLIENT_UIMS_VALIDATE_CODE_EXCEED("A0341", "用户验证码尝试次数超限"),
    CLIENT_UIMS_SEND_EAIL_INVALID("A0440","邮箱发送失败"),
    CLIENT_UIMS_LOGIN_EAIL_NULL("A0442","请输入邮箱验证码"),
    CLIENT_UIMS_SMS_EAIL_INVALID("A0540","短信发送失败"),
    CLIENT_UIMS_LOGIN_SMS_NULL("A0542","请输入短信验证码"),
    CLIENT_UIMS_CODES_INVALID("A0555", "验证码错误"),

    /**
     * 二级宏观错误码
     * 用户注册错误（A0400 - A0499）
     */
    CLIENT_REG("A0400", "用户注册错误"),
    CLIENT_REG_NO_AGREE_PRIVACY("A0401", "用户未同意隐私协议"),
    CLIENT_REG_AREA_LIMIT("A0402", "注册国家或地区受限"),
    CLIENT_REG_NAME_INVALID("A0410", "用户名不合法"),
    CLIENT_REG_NAME_EXIST("A0411", "用户名已存在"),
    CLIENT_REG_NAME_SENSITIVE("A0412", "用户名包含敏感词"),
    CLIENT_REG_NAME_SPECIAL("A0413", "用户名包含特殊字符"),
    CLIENT_REG_NAME_NOT_EXISTS("A0414", "请输入用户名"),

    CLIENT_REG_PASSWORD_INVALID("A0420", "密码校验失败"),
    CLIENT_REG_PASSWORD_SHORT("A0421", "密码长度不够"),
    CLIENT_REG_PASSWORD_WEAK("A0422", "密码强度不够"),

    CLIENT_REG_CODE_INVALID("A0430", "校验码输入错误"),
    CLIENT_REG_SMS_CODE_INVALID("A0431", "短信校验码输入错误"),
    CLIENT_REG_MAIL_CODE_INVALID("A0432", "邮件校验码输入错误"),
    CLIENT_REG_VOICE_CODE_INVALID("A0433", "语音校验码输入错误"),

    CLIENT_REG_CERT_INVALID("A0440", "用户证件异常"),
    CLIENT_REG_CERT_TYPE_NOT_CHOOSE("A0441", "用户证件类型未选择"),
    CLIENT_REG_ID_CARD_INVALID("A0442", "大陆身份证编号校验非法"),
    CLIENT_REG_PASSPORT_INVALID("A0443", "护照编号校验非法"),

    CLIENT_REG_BASE_INFO_INVALID("A0450", "用户基本信息校验失败"),
    CLIENT_REG_ADDRESS_INVALID("A0451", "地址格式校验失败"),

    CLIENT_REG_MOBILE_INVALID("A0460", "该手机号码格式不正确"),
    CLIENT_REG_MOBILE_BOUND("A0461", "该手机号码已被绑定"),
    CLIENT_REG_MOBILE_REGISTERED("A0462", "该手机号码已注册"),

    CLIENT_REG_EMAIL_INVALID("A0470", "该邮箱格式不正确"),
    CLIENT_REG_EMAIL_BOUND("A0471", "该邮箱已被绑定"),
    CLIENT_REG_EMAIL_REGISTERED("A0472", "该邮箱已注册"),
    CLIENT_REG_EMAIL_IS_NOT_USED("A0473", "用户名和邮箱不匹配"),

    /**
     * 客户端 - 产品服务异常
     */
    CLIENT_PRODUCT_EXISTS("A0501", "产品已存在"),
    CLIENT_PRODUCT_NOT_EXISTS("A0502", "产品不存在"),
    CLIENT_PRODUCT_CODE_EXISTS("A0503", "产品编码已存在"),
    CLIENT_PRODUCT_DELETED("A0504", "产品被删除"),
    /**
     * 当前不再预约服务时间内
     */
    OUT_OF_APPOINTMENT_TIME("A10030", "当前不再预约服务时间内！"),
    /**
     * 当前不再预约服务时间内
     */
    OUT_OF_APPOINTMENT_TIME1("A10031", "选择预约时间有误！"),
    /**
     * 当前律师未开启其他村预约功能
     */
    CLOSE_APPOINTMENT_FOR_OTHER_VILLAGE("A10031", "当前律师未开启其他村预约功能！"),
    /**
     * 离预约时间不足2小时，无法进行修改！   10034已被占用不可使用
     */
    LESS_THAN_TWO_HOURS("A10036", "离预约时间不足2小时，无法进行修改！"),
    /**
     * 预约未完成，无法评价！
     */
    CAN_NOT_EVALUATE("A10037", "预约未完成，无法评价！"),
    /** 法制微管家修改地址次数超过限制 */
    REPLY_DEADLINE_USERPAST("A10026", "由于律师48小时内未做回复，系统已自动关闭此留言咨询单，并已记录律师行为。"),
    REPLY_DEADLINE_LAWYERPAST("A10027", "由于您超过48小时未回复，系统已自动关闭此留言咨询单，记录未回复状态一次。"),
    REPLY_CLOSED("A10028", "该留言单已关闭，如有问题请重新提交留言咨询 。"),
    IDCARD_INFO_NULL("A10029", "身份证信息不能识别，请确认照片清晰！"),
    ID_CARD_INVALID("A10030", "无效的身份证号"),
    TAG_Not_LAW("A100281", "律师标记该留言为非法律问题，不再提供回复。"),
    ADDRESS_NO_SERVICE("A100282", "您当前定位的地区不在服务范围。"),
    STATUS_COMPLETED("A100283", "用户已评价该留言，该留言单已关闭，感想您的回复。"),
    TAG_Not_LAW_LAWYER("A100284", "已被您标注为非法律问题"),
    LAWYER_NO_EMPLOYMENT("A100286", "当前律师服务已到期，请返回首页重新发起留言咨询。"),
    LAWYER_NO_EMPLOYMENT1("A100287", "当前聘用记录已到期。"),
    /**
     * 超过预约时间！无法进行操作。
     */
    OVER_APPOINTMENT_TIME("A10038", "超过预约时间！无法进行操作。"),

    /**
     * 未获取到开通村镇信息！请确认。
     */
    OVER_APPOINTMENT_TIME1("A10039", "未获取到开通村镇信息！请确认。"),
    /**
     * 二级宏观错误码
     * 用户资源异常
     */
    CLIENT_RESOURCE("A0600", "用户资源异常"),
    CLIENT_RESOURCE_ACCOUNT_BALANCE_OVER("A0601", "当前账户余额不足"),
    CLIENT_RESOURCE_ACCOUNT_BALANCE_OVER1("A0611", "链上账户余额不足"),
    CLIENT_RESOURCE_DISK_SPACE_OVER("A0602", "用户磁盘空间不足"),
    CLIENT_RESOURCE_MEMORY_SPACE_OVER("A0603", "用户内存空间不足"),
    CLIENT_RESOURCE_OSS_SPACE_OVER("A0604", "用户 OSS 容量不足"),
    // 例如：每天抽奖数
    CLIENT_RESOURCE_USER_QUOTA_OVER("A0605", "用户配额已用光"),
    /** 该手机号已绑定其他微信号 */
    PHONE_IS_EXIST("A10021", "该手机号已绑定其他微信号"),
    /** 该身份证号已绑定其他微信号 */
    IDCARD_IS_EXIST("A10021", "该身份证号已绑定其他微信号"),
    /**
     * 二级宏观错误码
     * 用户上传文件异常
     */
    CLIENT_UPLOAD("A0700", "用户上传文件异常"),
    CLIENT_UPLOAD_FILE_TYPE_MISMATCH("A0701", "用户上传文件类型不匹配"),
    CLIENT_UPLOAD_FILE_TOO_LARGE("A0702", "用户上传文件太大"),
    CLIENT_UPLOAD_IMAGE_TOO_LARGE("A0703", "用户上传图片太大"),
    CLIENT_UPLOAD_VIDEO_TOO_LAGER("A0704", "用户上传视频太大"),
    CLIENT_UPLOAD_ZIP_FILE_TOO_LARGE("A0705", "用户上传压缩文件太大"),
    CLIENT_UPLOAD_ZIP_FILE_TOO_LARGE1("A0706", "文件上传Oss失败"),
    /**
     * 二级宏观错误码
     * 用户当前版本异常
     */
    CLIENT_VERSION("A0800", "用户当前版本异常"),
    CLIENT_VERSION_SYSTEM_MISMATCH("A0801", "用户安装版本与系统不匹配"),
    CLIENT_VERSION_TOO_LOW("A0802", "用户安装版本过低"),
    CLIENT_VERSION_TOO_HIGH("A0803", "用户安装版本过高"),
    CLIENT_VERSION_EXPIRED("A0804", "用户安装版本已过期"),
    CLIENT_VERSION_API_MISMATCH("A0805", "用户 API 请求版本不匹配"),
    CLIENT_VERSION_API_TOO_HIGH("A0806", "用户 API 请求版本过高"),
    CLIENT_VERSION_API_TOO_LOW("A0807", "用户 API 请求版本过低"),
    /**
     * 二级宏观错误码
     * 用户隐私未授权
     */
    CLIENT_PERMISSION("A0900", "用户隐私未授权"),
    CLIENT_PERMISSION_NOT_CONFIRM("A0901", "用户隐私未签署"),
    CLIENT_PERMISSION_NO_CAMERA("A0902", "用户摄像头未授权"),
    CLIENT_PERMISSION_NO_CAMERA_SOFT("A0903", "用户相机未授权"),
    CLIENT_PERMISSION_NO_PICTURE_LIB("A0904", "用户图片库未授权"),
    CLIENT_PERMISSION_NO_FILE("A0905", "用户文件未授权"),
    CLIENT_PERMISSION_NO_LOCATION("A0906", "用户位置信息未授权"),
    CLIENT_PERMISSION_NO_MAIL_LIST("A0907", "用户通讯录未授权"),
    /**
     * 二级宏观错误码
     * 用户设备异常
     */
    CLIENT_DEVICE("A1000", "用户设备异常"),
    CLIENT_DEVICE_CAMERA("A1001", "用户相机异常"),
    CLIENT_DEVICE_MICROPHONE("A1002", "用户麦克风异常"),
    CLIENT_DEVICE_RECEIVER("A1003", "用户听筒异常"),
    CLIENT_DEVICE_SPEAKER("A1004", "用户扬声器异常"),
    CLIENT_DEVICE_GPS_LOCATION("A1005", "用户 GPS 定位异常"),

    CLIENT_SERVICE("A1100", "用户请求服务异常"),
    CLIENT_SERVICE_REQUEST_COUNT_EXCEED("A1101", "请求次数超出限制"),
    CLIENT_SERVICE_REQUEST_CONCURRENT_EXCEED("A1102", "请求并发数超出限制"),
    CLIENT_SERVICE_OPERATION_WAITING("A1103", "用户操作请等待"),
    CLIENT_SERVICE_WEB_SOCKET_EXCEPTION("A1104", "WebSocket 连接异常"),
    CLIENT_SERVICE_WEB_SOCKET_DISCONNECT("A1105", "WebSocket 连接断开"),
    CLIENT_SERVICE_REQUEST_DUPLICATE("A1106", "用户重复请求"),
    CLIENT_SERVICE_REQUEST_NOT_FOUND("A1107", "用户请求未找到"),
    CLIENT_SERVICE_REQUEST_DATA_DUPLICATE("A1108", "用户请求数据重复"),

    /**
     * 二级宏观错误码
     * 微管家异常
     */
    CLIENT_LAWYER_IS_NULL("A1201","未查询到服务该村的律师信息！"),
    TELEPHONE_NUMBER_NOT_EXIST("A1202","您的手机号未被平台录入，请联系管理员进行添加"),
    LAWYER_PHONE_IS_EXIST("A1203", "该手机号已绑定其他律师，请确认后重新添加。"),
    /**
     * 二级宏观错误码
     * 系统执行超时
     */
    SYSTEM_TIMEOUT_RUNNING("B0100", "系统执行超时"),
    SYSTEM_TIMEOUT_ORDER_PROCESS("B0101", "系统订单处理超时"),
    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200", "系统容灾功能被触发"),
    SYSTEM_CURRENT_LIMITING("B0210", "系统限流"),
    SYSTEM_DEMOTION("B0220", "系统功能降级"),
    /**
     * 二级宏观错误码
     * 系统资源异常
     */
    SYSTEM_RESOURCE("B0300", "系统资源异常"),
    SYSTEM_RESOURCE_OVER("B0310", "系统资源耗尽"),
    SYSTEM_RESOURCE_DISK_OVER("B0311", "系统磁盘空间耗尽"),
    SYSTEM_RESOURCE_MEMORY_OVER("B0312", "系统内存耗尽"),
    SYSTEM_RESOURCE_FILE_HANDLE_OVER("B0313", "文件句柄耗尽"),
    SYSTEM_RESOURCE_CONNECT_POOL_OVER("B0314", "系统连接池耗尽"),
    SYSTEM_RESOURCE_THREAD_POOL_OVER("B0315", "系统线程池耗尽"),
    SYSTEM_RESOURCE_ACCESS_DENIED("B0320", "系统资源访问异常"),
    SYSTEM_RESOURCE_READ_DISK_FILE_FAILED("B0321", "系统读取磁盘文件失败"),

    /**
     * 二级宏观错误码
     * 中间件服务出错
     */
    THIRD_MIDDLE("C0100", "中间件服务出错"),
    THIRD_MIDDLE_RPC("C0110", "RPC 服务出错"),
    THIRD_MIDDLE_RPC_NOT_FOUND("C0111", "RPC 服务未找到"),
    THIRD_MIDDLE_RPC_NOT_REGISTER("C0112", "RPC 服务未注册"),
    THIRD_MIDDLE_INTERFACE_NOT_EXIST("C0113", "接口不存在"),
    THIRD_MIDDLE_MESSAGE("C0120", "消息服务出错"),
    THIRD_MIDDLE_MESSAGE_SEND_FAILED("C0121", "消息投递出错"),
    THIRD_MIDDLE_MESSAGE_CONSUME_FAILED("C0122", "消息消费出错"),
    THIRD_MIDDLE_MESSAGE_SUBSCRIBE_FAILED("C0123", "消息订阅出错"),
    THIRD_MIDDLE_MESSAGE_GROUP_NOT_FOUND("C0124", "消息分组未查到"),
    THIRD_MIDDLE_CACHE("C0130", "缓存服务出错"),
    THIRD_MIDDLE_CACHE_KEY_TOO_LONG("C0131", "key 长度超过限制"),
    THIRD_MIDDLE_CACHE_VALUE_TOO_LONG("C0132", "value 长度超过限制"),
    THIRD_MIDDLE_CACHE_STORAGE_OVER("C0133", "存储容量已满"),
    THIRD_MIDDLE_CACHE_DATA_FORMAT_NOT_SUPPORT("C0134", "不支持的数据格式"),
    THIRD_MIDDLE_CONFIG_SERVICE("C0140", "配置服务出错"),
    THIRD_MIDDLE_NET_RESOURCE_ERROR("C0150", "网络资源服务出错"),
    THIRD_MIDDLE_VPN_ERROR("C0151", "VPN 服务出错"),
    THIRD_MIDDLE_CDN_ERROR("C0152", "CDN 服务出错"),
    THIRD_MIDDLE_DNS_ERROR("C0153", "域名解析服务出错"),
    THIRD_MIDDLE_GATEWAY_ERROR("C0154", "网关服务出错"),
    /**
     * 二级宏观错误码
     * 第三方系统执行超时
     */
    THIRD_TIMEOUT("C0200", "第三方系统执行超时"),
    THIRD_TIMEOUT_RPC("C0210", "RPC 执行超时"),
    THIRD_TIMEOUT_MESSAGE_SEND("C0220", "消息投递超时"),
    THIRD_TIMEOUT_CACHE("C0230", "缓存服务超时"),
    THIRD_TIMEOUT_CONFIG("C0240", "配置服务超时"),
    THIRD_TIMEOUT_MYSQL("C0250", "数据库服务超时"),
    /**
     * 二级宏观错误码
     * 数据库服务出错
     */
    THIRD_DATABASE("C0300", "数据库服务出错"),
    THIRD_DATABASE_TABLE_NOT_FOUND("C0311", "表不存在"),
    THIRD_DATABASE_COLUMN_NOT_FOUND("C0312", "列不存在"),
    THIRD_DATABASE_INCLUDE_SAME_COLUMN("C0321", "多表关联中存在多个相同名称的列"),
    THIRD_DATABASE_DEADLOCK("C0331", "数据库死锁"),
    THIRD_DATABASE_PRIMARY_KEY_CONFLICT("C0341", "主键冲突"),
    THIRD_DATABASE_INSERT_FAILURE("C0351", "数据库插入失败"),
    THIRD_DATABASE_UPDATE_FAILURE("C0352", "数据库更新失败"),
    THIRD_DATABASE_DELETE_FAILURE("C0353", "数据库删除失败"),
    THIRD_DATABASE_SELECT_FAILURE("C0354", "查询无记录"),
    /**
     * 二级宏观错误码
     * 第三方容灾系统被触发
     */
    THIRD_DISASTER_RECOVERY("C0400", "第三方容灾系统被触发"),
    THIRD__CURRENT_LIMITING("C0401", "第三方系统限流"),
    THIRD_DEMOTION("C0402", "第三方功能降级"),
    /**
     * 二级宏观错误码
     * 通知服务出错
     */
    THIRD_NOTICE("C0500", "通知服务出错"),
    THIRD_NOTICE1("C0500", "交易失败"),
    THIRD_NOTICE_SMS_FAILED("C0501", "短信提醒服务失败"),
    THIRD_NOTICE_VOICE_FAILED("C0502", "语音提醒服务失败"),
    THIRD_NOTICE_EMAIL_FAILED("C0503", "邮件提醒服务失败");

    private String code;
    private String message;
}
