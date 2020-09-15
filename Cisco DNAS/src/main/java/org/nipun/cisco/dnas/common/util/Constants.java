/**
 *
 */
package org.nipun.cisco.dnas.common.util;

import org.nipun.cisco.dnas.utils.ApplicationProperties;

/**
 * @author ravichand.a
 *
 */
public interface Constants {
    public static final String ENTITY = "entity";
    public static final String APPLICATION_X_PDF = "application/x-pdf";
    public static final String DELETED = "_deleted";
    public static final String ENTRY = "entry";
    public static final String UP = "Up";
    public static final String DOWN = "Down";
    public static final String NULL = "null";
    public static final String EXIT = "exit";
    public static final String EXCITER = "Exciter";
    public static final String PASSIVE_READER = "passiveReader";
    public static final String ZONE = "zone";
    public static final String NORMAL = "Normal";
    public static final String SIGNAL_JUMP = "SignalJump";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String ALERT_TIME_INTERVAL = "Alert Time Interval";
    public static final String WEBEX = "WebEx";
    public static final String INFRA_HEALTH_DashBoard_Days = "InfraHealthDashBoardDays";
    public static final String ALERT_AUTO_CLOSE_TIME = "Alert Auto Close Time";
    public static final String EXCITER_TYPE = "exciterType";
    public static final String ALLOCATION = "Allocation";
    public static final String SIGNAL_LIGHT = "SignalLight";
    public static final String ENTRY_EXIT = "EntryExit";
    public static final String CONTRACTOR_TYPE = "ContractorType";
    public static final String CONTRACTOR = "Contractor";
    public static final String DRIVER_NAME = "DriverName";
    public static final String DESIGNATION = "Designation";
    public static final String EMP_NAME = "EmpName";
    public static final String EMPLOYEE_NAME = "Employee Name";
    public static final String RESPONSE_CODE = "responseCode";
    public static final String RESPONSE_DATA = "responseData";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String PEOPLE = "People";
    public static final String ASSET = "Asset";
    public static final String VEHICLE = "Vehicle";
    public static final String INFRA = "Infra";
    public static final String ACCESS_GATEWAY = "Access Gateway";
    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String ALERT = "ALERT";

    public static final String STR_200 = "200";
    public static final String STR_401 = "401";
    public static final String STR_402 = "402";
    public static final String STR_403 = "403";
    public static final String STR_404 = "404";
    public static final String STR_405 = "405";
    public static final String STR_406 = "406";
    public static final String STR_407 = "407";

    public static final String NOT_ALLOCATED = "NotAllocated";
    public static final String DE_ALLOCATED = "DeAllocate";
    public static final String ALLOCATED = "Allocated";

    public static final String N = "N";
    public static final String Y = "Y";

    public static final String NVIEW_STATUS = "Status";
    public static final String NVIEW = "nview";
    public static final String TEMP = "temp";
    public static final String SMALL_STATUS = "status";
    public static final String STATUS = "status";
    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "InActive";
    public static final String OPEN = "Open";
    public static final String CLOSE = "Close";

    public static final String STR_408 = "408";
    public static final String STR_409 = "409";
    public static final String STR_410 = "410";
    public static final String STR_411 = "411";
    public static final String STR_412 = "412";
    public static final String STR_413 = "413";
    public static final String STR_414 = "414";
    public static final String STR_415 = "415";
    public static final String STR_416 = "416";
    public static final String STR_417 = "417";
    public static final String STR_418 = "418";
    public static final String STR_419 = "419";
    public static final String STR_420 = "420";
    public static final String STR_421 = "421";
    public static final String STR_422 = "422";
    public static final String STR_423 = "423";
    public static final String STR_424 = "424";
    public static final String STR_425 = "425";
    public static final String STR_426 = "426";
    public static final String STR_427 = "427";
    public static final String STR_428 = "428";
    public static final String STR_429 = "429";
    public static final String STR_430 = "430";
    public static final String STR_431 = "431";
    public static final String STR_432 = "432";
    public static final String STR_433 = "433";
    public static final String STR_434 = "434";
    public static final String STR_435 = "435";
    public static final String STR_436 = "436";
    public static final String STR_437 = "437";
    public static final String STR_438 = "438";
    public static final String STR_439 = "439";
    public static final String STR_440 = "440";
    public static final String STR_441 = "441";
    public static final String STR_442 = "442";
    public static final String CHANGE_PASSWORD = "ChangePassword";
    public static final String AUTHENCATION = "Authentication";
    public static final String LDAPSLIST = "LdapsList";
    public static final String JACKSON_VIEW = "jacksonView";

    public final static String Session_Timeout = "Session-Timeout";
    public final static String Session_Created = "Session-Created";
    public final static String Logout = "Logout";
    public final static String TRIP_REPORT = "Trip_Report";
    public final static String VEHICLE_REPORT = "Vehicle Report";
    public final static String IPAddress = "IPAddress";

    public final static String MAP = "map";
    public final static String ID = "id";
    public final static String IMPORTED_ID = "importedId";
    public final static String NAME = "name";
    public final static String RELATION_SHIP_DATA = "relationshipData";
    public final static String CHILDREN = "children";
    public final static String DETAILS = "details";
    public final static String DNA = "DNA";
    public final static String UI = "UI";

    public static final String IP = ApplicationProperties.getProperty("MapCallingIP");
    public static final String MAP_URL = "https://" + IP + "/api/config/v1/maps";
    public static final String MAP_URL_BY_CAMPUS = "https://" + IP + "/api/config/v1/maps/info";
    public static final String IMAGE_URL_BY_CAMPUS_BUILDING_FLOOR = "https://" + IP + "/api/config/v1/maps/image";
    public static final String GET_LOCATION_BY_MACADDRESS = "https://" + IP + "/api/location/v2/clients?macAddress=";
    public static final String CAMPUSES = "campuses";
    public static final String BUILDING_LIST = "buildingList";
    public static final String FLOOR_LIST = "floorList";
    public static final String ZONES = "zones";
    public static final String ZONE_CO_ORDINATE = "zoneCoordinate";
    public static final String NOTIFICATIONS = "notifications";
    public static final String DEVICE_ID = "deviceId";
    public static final String LOCATION_CO_ORDINATE = "locationCoordinate";
    public static final String LOCATION_MAP_HIERARCHY = "locationMapHierarchy";
    public static final String X = "x";
    public static final String SMALL_Y = "y";
    public static final String Z = "z";
    public static final String UNIT = "unit";
    public static final String CEO_Office = "CEO Office";
    public static final String Directors_Office = "Directors Office";
    public static final String Conference_room_1 = "Conference room-1";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String FLOOR_ID = "aesUidString";
    public static final String PROCESS_INDICATOR_U = "U";
    public static final String PROCESS_INDICATOR_P = "P";
    public static final String NOTIFICATION_TYPE = "notificationType";
    public static final String LOCATION_UPDATE = "locationupdate";
    public static final String AP_MAC_ADDRESS = "apMacAddress";
    public static final String APPLICATION_JSON = "application/json";

    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";
    public static final String STATUS_2 = "2";
    public static final String MESSAGE = "message";
    public static final String MSG = "msg";
    public static final String ERR_MSG = "errMsg";
    public static final String SUCCESS = "success";

    public static final String AVAILABLE = "Available";
    public static final String BOOKED = "Booked";
    public static final String MAP_HIERACHY_STRING = "mapHierarchyString";
    public static final String BOOKING_STATUS = "bookingStatus";
    public static final String U = "U";
    public static final String AUTHENCATION_MOBILE = "Authentication_Mobile";
    public static final String AUTHENCATION_MODE = "Authentication_Mode";
    public static final String CLOSED = "Closed";
    public static final String MENU_NAME = "Reports";
    public static final String SUB_MODULE_NAME = "TmsAdmin";
    public static final String SUB_MENU_NAME = "SLA Report Internal";

    public static final String MODULE_CMX = "nview";
    public static final String SUB_MODULE_CMX = "nview";
    public static final String DASHBOARD = "Dashboard";
    public static final String CONFERENCE_BOOKING = "Conference_booking";
    public static final String MY_BOOKINGS = "My_bookings";
    public static final String CREATE_DESK_BOOKING = "Create_desk_booking";
    public static final String EXTEND_CONFERENCE = "extend_conference";
    public static final String EXTEND_DESK = "extend_desk";
    public static final String CANCEL_DESK = "cancel_desk";
    public static final String END_CONFERENCE = "end_conference";
    public static final String CANCEL_CONFERENCE = "cancel_conference";
    public static final String RAISE_TICKET = "raise_ticket";

    public static final String DESK_BOOKING = "Desk_booking";
    public static final String MY_DESK_BOOKINGS = "My_bookings";
    public static final String END_DESK = "end_desk";
    public static final String TICKET_MANAGEMENT = "Ticket_management";
    public static final String ASSIGNED_TICKETS = "Assigned_tickets";
    public static final String MY_TICKETS = "My_tickets";
    public static final String ORDER_FOOD = "Order_food";
    public static final String NOTIFICATIONS_MENU = "Notifications";
    public static final String REPORTS = "Reports";
    public static final String CONFERENCE_UTILIZATION = "Conference_utilization";
    public static final String DESK_UTILIZATION = "Desk_utilization";
    public static final String CONFIGURATIONS = "Configurations";
    public static final String CAMPUS = "Campus";
    public static final String CREATE_CAMPUS = "Create_campus";
    public static final String VIEW_CAMPUS = "View_campus";
    public static final String EDIT_CAMPUS = "Edit_campus";

    public static final String BUILDING = "Building";
    public static final String CREATE_BUILDING = "Create_building";
    public static final String VIEW_BUILDING = "View_building";
    public static final String EDIT_BUILDING = "Edit_building";
    public static final String DELETE = "Delete";

    public static final String FLOOR = "Floor";
    public static final String CREATE_FLOOR = "Create_floor";
    public static final String VIEW_FLOOR = "View_floor";
    public static final String EDIT_FLOOR = "Edit_floor";

    public static final String CATEGORY = "Category";
    public static final String CREATE_CATEGORY = "Create_category";
    public static final String VIEW_CATEGORY = "View_category";
    public static final String EDIT_CATEGORY = "Edit_category";

    public static final String CREATE_ZONE = "Create_zone";
    public static final String VIEW_ZONE = "View_zone";
    public static final String EDIT_ZONE = "Edit_zone";

    public static final String FACILITIES = "Facilities";
    public static final String CREATE_FACILITIES = "Create_facilities";
    public static final String VIEW_FACILITIES = "View_facilities";
    public static final String EDIT_FACILITIES = "Edit_facilities";

    public static final String CONFERENCE = "Conference";
    public static final String CREATE_CONFERENCE = "Create_conference";
    public static final String VIEW_CONFERENCE = "View_conference";
    public static final String EDIT_CONFERENCE = "Edit_conference";

    public static final String DESK = "Desk";
    public static final String CREATE_DESK = "Create_desk";
    public static final String VIEW_DESK = "View_desk";
    public static final String EDIT_DESK = "Edit_desk";

    public static final String ICON = "Icon";
    public static final String CREATE_ICON = "Create_icon";
    public static final String VIEW_ICON = "View_icon";
    public static final String EDIT_ICON = "Edit_icon";

    public static final String RESERVE = "Reserve";
    public static final String RESERVED = "Reserved";
    public static final String UN_RESERVED = "Un Reserved";
    public static final String RESERVE_DESK = "Reservedesk";
    public static final String RESERVE_CONFERENCE = "Reserveconference";

    public static final String CMX_API = "CmxApi";
    public static final String CMX_API_VIEW = "CmxApiView";
    public static final String CMX_API_CREATE = "CmxApiCreate";

    public static final String CONFERENCE_RESERVE = "conference_reserve";
    public static final String CONFERENCE_RESERVE_VIEW = "conference_reserve_view";
    public static final String CONFERENCE_RESERVE_CREATE = "conference_reserve_create";

    public static final String DESK_RESERVE = "desk_reserve";
    public static final String DESK_RESERVE_VIEW = "desk_reserve_view";
    public static final String DESK_RESERVE_CREATE = "desk_reserve_create";

    public static final String EMAIL = "email";
    public static final String EMAIL_CREATE = "email_create";
    public static final String EMAIL_EDIT = "email_edit";
    public static final String EMAIL_VIEW = "email_view";

    public static final String EQUIPMENT = "equipment";
    public static final String EQUIPMENT_CREATE = "equipment_create";
    public static final String EQUIPMENT_EDIT = "equipment_edit";
    public static final String EQUIPMENT_VIEW = "equipment_view";

    public static final String MAKE = "make";
    public static final String MAKE_CREATE = "make_create";
    public static final String MAKE_EDIT = "make_edit";
    public static final String MAKE_VIEW = "make_view";

    public static final String MODEL = "model";
    public static final String MODEL_CREATE = "model_create";
    public static final String MODEL_EDIT = "model_edit";
    public static final String MODEL_VIEW = "model_view";

    public static final String TBL_SWITCH = "tbl_switch";
    public static final String TBL_SWITCH_CREATE = "switch_create";
    public static final String TBL_SWITCH_EDIT = "switch_edit";
    public static final String TBL_SWITCH_VIEW = "switch_view";

    public static final String PORT_DESK = "port_desk";
    public static final String PORT_DESK_CREATE = "port_desk_create";
    public static final String PORT_DESK_EDIT = "port_desk_edit";
    public static final String PORT_DESK_VIEW = "port_desk_view";

    public static final String CNF_MAC = "conference_mac";
    public static final String CNF_MAC_CREATE = "conference_mac_create";
    public static final String CNF_MAC_EDIT = "conference_mac_edit";
    public static final String CNF_MAC_VIEW = "conference_mac_view";

    public static final String NOT_VERIFIED = "NotVerified";
    public static final String VERIFIED = "Verified";

    public static final String STARTED = "Started";
    public static final String SENT = "Sent";
    public static final String REMINDER1 = "Reminder1 Sent";
    public static final String REMINDER2 = "Reminder2 Sent";

    public static final String LOCATION_PRESENCE = "location_presence";
    public static final String LOCATION_PRESENCE_CREATE = "location_presence_create";
    public static final String LOCATION_PRESENCE_EDIT = "location_presence_edit";
    public static final String LOCATION_PRESENCE_VIEW = "location_presence_view";

    public static final String NOTIFICATION_ENGINE = "notification_engine";
    public static final String NOTIFICATION_ENGINE_CREATE = "notification_engine_create";
    public static final String NOTIFICATION_ENGINE_EDIT = "notification_engine_edit";
    public static final String NOTIFICATION_ENGINE_VIEW = "notification_engine_view";

    public static final String SELF_REGISTRATION = "self_registration";

    public static final String USER_PHONE_CONFIG = "user_phone_config";
    public static final String USER_PHONE_CREATE = "user_phone_create";
    public static final String USER_PHONE_EDIT = "user_phone_edit";
    public static final String USER_PHONE_VIEW = "user_phone_view";

    public static final String PHONE_EXTENSION = "phone_extension";
    public static final String PHONE_EXTENSION_CREATE = "phone_extension_create";
    public static final String PHONE_EXTENSION_EDIT = "phone_extension_edit";
    public static final String PHONE_EXTENSION_VIEW = "phone_extension_view";

    public static final String DISCLAIMER = "disclaimer";

    public static final String MAIL_BODY_CONFERENCE = "Your booking for the following conference room is";
    public static final String MAIL_BODY_DESK = "Your booking for the following flexi desk is";
    public static final String CANCELLED = "Cancelled";
    public static final String MAIL_BODY_DESK_STATUS = "Your booking for the following flexi desk is";
    public static final String MAIL_BODY_DESK_RESERVE_STATUS = "Your reservation for the following flexi desk is";
    public static final String SCHEDULED = "Scheduled";
    public static final String COMPLETED = "Completed";
    public static final String INPROGRESS = "InProgress";
    public static final String END = "Ended";
    public static final String MAIL_BODY_CONFERENCE_STATUS = "Your booking for the following conference room is";
    public static final String MAIL_BODY_CONFERENCE_RESERVE_STATUS = "Your reservation for the following conference room is";
    public static final String MAIL_BODY_DESK_SUCESS = "Your following flexi desk booking is successful.";
    public static final String MAIL_BODY_DESK_RESERVE_SUCESS = "Your following flexi desk reservation is successful.";
    public static final String DESK_FACILITIES = "desk_facilities";

    // Error messages
    public static final String CONFERENCE_BOOKING_ERR_MSG = "Unable to book the conference room";
    public static final String EMAIL_SEARCH_ERR_MSG = "No records found";
    public static final String CONFERENCE_CANCEL_ERR_MSG = "Unable to cancel conference booking";
    public static final String CONFERENCE_RESERVE_ERR_MSG = "Unable to reserve conference booking";
    public static final String DESK_CANCEL_ERR_MSG = "Unable to cancel booking. Please try later";
    public static final String CONFERENCE_OUTLOOK_TO_MAILS_ERR_MSG = "To mail is not available, Unable to send mail";
    public static final String CONFERENCE_OUTLOOK_ERR_MSG = "Unable to send mail";
    public static final String CONFERENCE_EXTEND_NEXT_BOOKING_ERR_MSG = "Unable to extend meeting request, Next meeting was booked";

    public static final String DESK_EXTEND_NEXT_BOOKING_ERR_MSG = "Unable to extend desk booking request, Next booking exist";

    public static final String CONFERENCE_EXTEND_TO_TIME_ERR_MSG = "Extended time should greater than booked To time";
    public static final String CONFERENCE_EXTEND_BOOKING_NOT_EXIST_ERR_MSG = "Failed to extend conference room booking request";

    public static final String DESK_EXTEND_BOOKING_NOT_EXIST_ERR_MSG = "Failed to extend desk booking request";

    public static final String CONFERENCE_END_ERR_MSG = "Failed to end conference room booking request";
    public static final String CONFERENCE_END_NOT_BOOKED_ERR_MSG = "Conference room was not booked by you";

    public static final String BOOKING_LIMIT_EXCEEDED = "Your booking limit exceeded for the day.";
    public static final String CONF_QR_NOT_BOOKED_ERR_MSG = "This conference room is not booked by you";
    public static final String DESK_QR_NOT_BOOKED_ERR_MSG = "This desk was not booked by you";
    public static final String CONF_QR_NOT_MATCHED_ERR_MSG = "QR Code mismatched";
    public static final String CONF_QR_NOT_EXIST_ERR_MSG = "QR code does not exist";
    public static final String CONF_QR_CONF_NOT_EXIST_ERR_MSG = "The conference room does not exist";
    public static final String DESK_QR_CONF_NOT_EXIST_ERR_MSG = "The desk does not exist";
    public static final String DESK_BOOKING_ERR_MSG = "Unable to book the desk";
    public static final String USERNAME_INCORRECT_ERR_MSG = "Username incorrect";
    public static final String PASSWORD_INCORRECT_ERR_MSG = "Invalid password";
    public static final String EXTEND_LIMIT_EXCEEDED = "Your extended time limit exceeded for the day.";
    public static final String VERTICES = "vertices";
    public static final String UN_EXPECTED_ERROR = "Un expecetd error occured, please contact admin";
    public static final String RESOURCE_ICONS = "resource_icons";
    public static final String PERSON_NAME = "EntityName";
    public static final String PERSON_ID = "EntityId";
    public static final String EMPLOYEE_ID = "Employee Id";
    public static final String ENTITY_NAME = "EntityName";
    public static final String ENTITY_ID = "EntityId";
    public static final String MAC_ID = "MacId";
    public static final String FR = "FR";
    public static final String FR_ENTITY_ENROLL = "Fr_Entity_Enroll";
    public static final String ADD_PHOTO = "Add_Photo_In_Fr";
    public static final String CREATEALBUM = "Createalbum";
    public static final String DELETEALBUM = "DeleteAlbum";
    public static final String REMOVEUSER = "RemoveUser";
    public static final String RECOGNIZE_ENTITY = "Recognize_Entity";
    public static final String THREMAL_SCANS = "Abnormal Body Temperature";
    public static final String MRKPLUGIN = "mrkPlugin";
    public static final String CISCODNASFIREHOUSE = "ciscoDNASFireHouse";
    public static final String SYSTEM_RESERVED = "System_Reserved";

    public static final String SAFE = "Safe";
    public static final String EVACUATED = "Evacuated";
    public static final String ZONE_ID = "ZoneId";
    public static final String MAP_ID = "MapId";
    public static final String X_Coordinate = "X_Coordinate";
    public static final String Y_Coordinate = "Y_Coordinate";
    public static final String CISCO_DNAS = "cisco_dnas";
    public static final String VISITOR = "Visitor";
    public static final String PHONENO = "Phone No";
    public static final String STRING = "String";
    public static final String partnerTenantId = "partnerTenantId";
    public static final String activationRefId = "activationRefId";
    public static final String version = "version";
    public static final String expiresIn = "expiresIn";
    public static final String baseUrl = "baseUrl";
    public static final String appId = "appId";
    public static final String tenantId = "tenantId";
    public static final String exp = "exp";
    public static final String iat = "iat";
    public static final String customer = "customer";
    public static final String UN_KNOWN_PERSON = "Unknown Person";
    public static final String EMPLOYEE = "Employee";
    public final static String mapId = "Map Id";
    public final static String Level = "Level";
    public final static String zoneId = "Zone Id";
    public final static String FORGOT = "forgotPassword";
    public final static String CMXZONE = "CMXZONE";
    public final static String UPDATE = "Update";
    public final static String CREATE = "Create";
    public static final String FR_ENTITY_ENROLL_MULTIPLE = "Enroll_With_Photos";
    public static final String FR_UPDATE_MULTIPLE = "Update_Users";
    public static final String TRIGGER_EVENT = "Trigger_Event";
    public static final String CAMERAS = "Cameras";

    public static final String AUTHENCATION_URL = "url";
    //need to check

    /* public static final String STATUS = "status";
    public static final String ZONE = "Zone";*/
}
