package vn.ntlogistics.app.ordermanagement.Commons.Sqlite;


import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary.ItemBill;


public class Variables {
	// LINK
	public static String DB_PATH = "";
	// DB
	public final static int DATABASE_VERSION = 3;
	public static final String SP_KEY_DB_VER = "db_ver";
	public final static String DATABASE_NAME = "MyStaff";
	public final static String USER_ACCOUNT = "UserAccount";
	public final static String USER_LOCALKEY = "UserLocalkey";
	public final static String USER_PUBLICKEY = "UserPublicKey";
	// STAFF
	public final static String TBL_STAFF = "tblStaff";
	public final static String KEY_STAFF_ID = "id_staff";
	public final static String KEY_PUBLIC_KEY = "publickey";
	public final static String KEY_LOCAL_KEY = "localkey";
	public final static String KEY_VALUE_STAFF = "value_staff";
	public final static String KEY_MYBANK = "myBank";
	public final static String KEY_ISPICK = "isPick";
	// CITY
	public final static String TBL_CITY = "tblCity";
	public final static String KEY_CITY_ID = "C_City_Id";
	public final static String KEY_NAME_CITY = "name";
	public final static String KEY_AREACODE = "areacode";
	public final static String KEY_ID_POSITON = "id_positon";
	// DISTRICT
	public final static String TBL_DISTRICT = "tblDistrict";
	public final static String KEY_FK_CITY_ID = "fk_c_city_id";
	public final static String KEY_NAME_DISTRICT = "name";
	public final static String KEY_DISTRICT_ID = "th_district_id";
	public final static String KEY_DISTRICT_VALUE = "value";
	// BILLFAIL
	public final static String TBL_BILLFAIL = "tblBillFail";
	public final static String KEY_BILL_ID = "id_bill";
	public final static String KEY_BILL = "bill";
	public final static String KEY_TKH = "tkh";
	public final static String KEY_MKH = "mkh";
	public final static String KEY_MONEY = "money";
	public final static String KEY_MONEYCOD = "moneycod";
	public final static String KEY_TINH = "tinh";
	public final static String KEY_QUAN = "quan";
	public final static String KEY_TL = "TL";
	public final static String KEY_SL = "SL";
	public final static String KEY_TLQD = "TLQD";
	public final static String KEY_STATUS = "status";
	public final static String KEY_ISDO = "isDO";
	public final static String KEY_SOKIENDO = "SK";

	// BILLDO
	public final static String TBL_BILLFAILDO = "tblBillFailDO";
	public final static String KEY_BILL_DO_ID = "id_bill_DO";
	public final static String KEY_BILL_DO = "billDO";
	public final static String KEY_STATUS_DO = "statusDO";

	// Service
	public final static String TBL_SERVICE = "tblService";
	public final static String KEY_ID_SERVICE = "id_Service";
	public final static String KEY_VALUE_SERVICE = "svalue";
	public final static String KEY_NAME_SERVICE = "name";

	// Guest
	public final static String TBL_GUEST = "tblGuest";
	public final static String KEY_GUEST_ID = "id_Guest";
	public final static String KEY_GUEST_NAME = "nameGuest";

	//TODO: Version 3 _____________________________________

	//Table: SenderBill
	public static final String TBL_SENDER_BILL = "tblSenderBill";
	public static final String SB_ID = "billID";
	public static final String SB_SENDER_PHONE = "senderNumberPhone";
	public static final String SB_SENDER_ADDRESS = "senderAddress";
	public static final String SB_SENDER_NAME = "senderName";
	public static final String SB_SENDER_NODE = "senderNode";
	public static final String SB_RECEIVER_PHONE = "receiverNumberPhone";
	public static final String SB_RECEIVER_ADDRESS = "receiverAddress";
	public static final String SB_RECEIVER_NAME = "receiverName";
	public static final String SB_RECEIVER_NODE = "receiverNode";
	public static final String SB_LENGTH = "length";
	public static final String SB_WIDTH = "width";
	public static final String SB_HEIGHT = "height";
	public static final String SB_WEIGHT = "weight";
	public static final String SB_COD = "cod";
	public static final String SB_SERVICE = "service";
	public static final String SB_STATUS = "status";
	public static final String SB_PROVINCE_ID = "senderProvinceID";
	public static final String SB_SEND_DATE = "sendDate";
	public static final String SB_OTP_CODE = "otpCode";

	//TODO: Version 3 _____________________________________End/



	// Variables Intent
	public final static int AT_BILLRIGHT = 1;
	public final static int AT_BILLFAIL = 0;
	public final static int BILLDO = 12;
	public final static int BILLNT = 11;
	public final static int BILLW = 10;
	public final static int BILLCROP = 9;
	public final static int CROPDONE = 8;
	public final static int THISISMENU = 100;
	public final static int THISISWHITE = 99;
	public final static int THISISPRICE = 69;
	public final static int THISISDONE = 86;

	public static int find;
	public static String account;
	public static String publickey;

	public static String bankAcc;
	public static String bankKey;

	public static int fuckAcc = 0;

	// Price send to WhiteBill
	public static String SK, SLKD, Service, TL, TLQD, Long, Large, Height,
			fCity, tCity, fDis, tDis;
	public static int POfCity, POtCity, POfDis, POtDis, POservice, IDPositon;

	public static int POWhttt = 1;

	public static String POWmkh, POWbill, POWfeedonggoi, POWfeenangha,
			POWfeebaohiem, POWfeekhac, POWfeeCOD, POWfeeChinh, POWfeeNTX,
			POWsdt, POWdcgui, POWfeePublicPostage;

	// Variables Crop
	public static ArrayList<ItemBill> LST_ItemBill = new ArrayList<ItemBill>();
}
