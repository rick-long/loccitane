package org.spa.serviceImpl.user;

import com.spa.constant.APIConstant;
import com.spa.constant.CommonConstant;
import com.spa.controller.api.MemberAPIVO;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.jxlsBean.MemberImportJxlsBean;
import com.spa.jxlsBean.importDemo.StaffImportJxlsBean;
import com.spa.thread.CommonSendEmailThread;
import com.spa.thread.RegistrationThread;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.*;
import org.spa.dao.member.MemberDao;
import org.spa.dao.prepaid.PrepaidDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.BookItem;
import org.spa.model.book.BookItemTherapist;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.payroll.PayrollAttributeKey;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.model.privilege.SysResource;
import org.spa.model.privilege.SysRole;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.*;
import org.spa.model.staff.*;
import org.spa.model.user.CommunicationChannel;
import org.spa.model.user.User;
import org.spa.model.user.UserCode;
import org.spa.model.user.UserGroup;
import org.spa.service.book.BookItemService;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.service.loyalty.UserLoyaltyLevelService;
import org.spa.service.marketing.MktMailShotService;
import org.spa.service.payroll.PayrollAttributeKeyService;
import org.spa.service.payroll.PayrollTemplateService;
import org.spa.service.privilege.SysRoleService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductService;
import org.spa.service.salesforce.UserMarketingCampaignTransactionService;
import org.spa.service.shop.AddressService;
import org.spa.service.shop.PhoneService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.*;
import org.spa.utils.*;
import org.spa.vo.app.member.*;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.loyalty.UserLoyaltyLevelVO;
import org.spa.vo.page.Page;
import org.spa.vo.staff.StaffInOrOutVo;
import org.spa.vo.staff.StaffPayrollAttributeVO;
import org.spa.vo.staff.StaffServiceSettingsVO;
import org.spa.vo.staff.StaffVO;
import org.spa.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.spa.constant.CommonConstant.*;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class UserServiceImpl extends BaseDaoHibernate<User> implements UserService {

    @Autowired
    public ShopService shopService;

    @Autowired
    public PhoneService phoneService;

    @Autowired
    public LoyaltyLevelService loyaltyLevelService;

    @Autowired
    public UserLoyaltyLevelService userLoyaltyLevelService;

    @Autowired
    public SysRoleService sysRoleService;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private MktMailShotService mktMailShotService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CommunicationChannelService communicationChannelService;

    @Autowired
    private PayrollAttributeKeyService payrollAttributeKeyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private StaffInOrOutService staffInOrOutService;

    @Autowired
    PayrollTemplateService payrollTemplateService;

    @Value("${role.admin}")
    private String admin;
    @Value("${role.therapist}")
    private String therapist;
    @Value("${role.shopManager}")
    private String shopManager;
    @Value("${role.receptionist}")
    private String receptionist;
    @Value("${role.operationalManager}")
    private String operationalManager;
    @Value("${role.marketing}")
    private String marketing;
    @Value("${role.hotelManager}")
    private String hotelManager;
    @Value("${role.shopMangerT}")
    private String shopMangerT;
    @Value("${role.receptionT}")
    private String receptionT;
    @Value("${role.liveChat}")
    private String liveChat;
    @Value("${role.sf}")
    private String sf;

//    
//    @Autowired
//    private PrepaidService prepaidService;

//    @Autowired
//    private PrepaidTopUpTransactionService prepaidTopUpTransactionService;

    @Autowired
    private PrepaidDao prepaidDao;


    @Autowired
    private MemberDao memberDao;

    @Autowired
    private UserMarketingCampaignTransactionService userMarketingCampaignTransactionService;

    @Autowired
    StaffHomeShopDetailsService staffHomeShopDetailsService;

    @Autowired
    private RoomService roomService;

    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public User getLoginUserByAccountWithType(String account, String accountType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", accountType));
        if (CommonConstant.USER_ACCOUNT_TYPE_MEMBER.equals(accountType)) {
            criteria.add(Restrictions.eq("activing", true)); // 必须是激活的账号才可以登录
        }
        criteria.add(Restrictions.or(Restrictions.eq("username", account), Restrictions.eq("email", account)));
//		3个or以上也可以这么实现		
//		Disjunction disjunction = Restrictions.disjunction(); 
//		disjunction.add(Restrictions.eq("username", account));
//		disjunction.add(Restrictions.eq("email", account));
//		criteria.add(disjunction);
        User user = get(criteria);
        if (user != null) {
            Hibernate.initialize(user.getShop());
            Hibernate.initialize(user.getCompany());
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username, String accountType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", accountType));
        criteria.add(Restrictions.eq("username", username));
        return get(criteria);
    }

    @Override
    public User getUserByEmail(String email, String accountType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", accountType));
        criteria.add(Restrictions.eq("email", email));
        return get(criteria);
    }

    public List<User> getAvalibleUsersByAccountTypeAndRoleRef(String accountType, String roleRef) {
        List<User> userList = new ArrayList<User>();
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", accountType));
        if (StringUtils.isNotBlank(roleRef)) {
            criteria.createAlias("sysRoles", "sysRole");
            criteria.add(Restrictions.eq("sysRole.reference", roleRef));
        }
        userList = list(criteria);
        return userList;
    }

    @Override
    public void saveOrUpdateStaff(StaffVO staffVO) {
        User staff = null;
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();
        Long id = staffVO.getId();
        if (id != null) {
            staff = get(id);
            staff.setLastName(loginUser);
            staff.setLastUpdated(now);
            staff.setLastUpdatedBy(loginUser);
            staff.getStaffPayrollAttributes().clear();
            staff.getSysRoles().clear();
            staff.getStaffHomeShops().clear();
            staff.getUserGroups().clear();
        } else {
            staff = new User();
            staff.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_STAFF);

            staff.setCompany(WebThreadLocal.getCompany());
            staff.setCreated(now);
            staff.setLastUpdated(now);
            staff.setLastUpdatedBy(loginUser);
            staff.setCreatedBy(loginUser);
            staff.setEnabled(true);
            staff.setPassword(EncryptUtil.SHA1(staffVO.getPassword()));

        }
        staff.setFirstName(staffVO.getFirstName());
        staff.setLastName(staffVO.getLastName());
        staff.setFullName(staffVO.getFirstName() + " " + staffVO.getLastName());
        staff.setDisplayName(staffVO.getDisplayName());
        staff.setHasMPF(staffVO.getHasMPF() != null ? staffVO.getHasMPF() : false);
        staff.setOptedOut(false);

        String username = staffVO.getFirstName().toUpperCase() + "_" + staffVO.getLastName().toUpperCase();
        if (StringUtils.isNoneBlank(username)) {
            if (id != null) {//EDIT
                if (!staff.getUsername().equals(username)) {
                    List<User> staffList = new ArrayList<User>();
                    DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
                    /*      criteria.add(Restrictions.eq("enabled", true));*/
                    criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
                    criteria.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
                    criteria.addOrder(Order.desc("created"));
                    staffList = list(criteria);
                    if (staffList != null && staffList.size() > 0) {
                        User originalStaff = staffList.get(0);
                        if (!originalStaff.getUsername().equals(staff.getUsername())) {
                            username = originalStaff.getUsername() + "_" + staffList.size();
                            staff.setCreated(now);
                            staff.setUsername(username);
                        }
                    } else {
                        staff.setUsername(username);
                    }
                }
            } else {//ADD
                List<User> staffList = new ArrayList<User>();
                DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
                /*      criteria.add(Restrictions.eq("enabled", true));*/
                criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
                criteria.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
                criteria.addOrder(Order.desc("lastUpdated"));
                staffList = list(criteria);
                if (staffList != null && staffList.size() > 0) {
                    User originalStaff = staffList.get(0);
                    if (!originalStaff.getUsername().equals(staff.getUsername())) {
                        username = originalStaff.getUsername() + "_" + staffList.size();
                        staff.setUsername(username);
                    } else {
                        staff.setUsername(username);
                    }
                } else {
                    staff.setUsername(username);
                }
            }

        }
        Long sysRoleId = staffVO.getSysRoleId();
        SysRole role = sysRoleService.get(sysRoleId);
        staff.getSysRoles().add(role);

        staff.setEmail(staffVO.getEmail());
        staff.setGender(staffVO.getGender());

        staff.setDateOfBirth(staffVO.getDateOfBirth());
        staff.setJoinDate(staffVO.getJoinDate());

        // set staff home shops
        Long[] homeShopIds = staffVO.getHomeShopIds();
        if (homeShopIds != null && homeShopIds.length > 0) {
            Shop shop = null;
            for (Long shopId : homeShopIds) {
                shop = shopService.get(shopId);
                staff.getStaffHomeShops().add(shop);
            }
        }
        //mobile
        if (StringUtils.isNotBlank(staffVO.getMobilePhone())) {
            List<Phone> mpList = phoneService.getPhonesByUserIdAndType(staff.getId(), CommonConstant.PHONE_TYPE_MOBILE);
            Phone mPhone = null;
            if (mpList != null && mpList.size() > 0) {
                mPhone = mpList.get(0);
            } else {
                mPhone = new Phone();
                mPhone.setUser(staff);
                mPhone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            }
            mPhone.setNumber(staffVO.getMobilePhone());
            staff.getPhones().add(mPhone);
        }
        //address
        if (staffVO.getAddressVO() != null) {
            AddressVO addrvo = staffVO.getAddressVO();
            Address address = null;
            if (staff.getAddresses() != null && staff.getAddresses().size() > 0) {
                address = staff.getAddresses().iterator().next();
                address.setLastUpdated(new Date());
                address.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
            } else {
                address = new Address();
                address.setCompany(WebThreadLocal.getCompany());
                address.setUser(staff);
                address.setCreated(new Date());
                address.setCreatedBy(WebThreadLocal.getUser().getUsername());
                address.setIsActive(true);
            }
            address.setAddressExtention(addrvo.getAddressExtention());
            address.setCity(addrvo.getCity());
            address.setCountry(addrvo.getCountry());
            address.setDistrict(addrvo.getDistrict());

            addressService.saveOrUpdate(address);
        }

        //staff group
        Long staffGroupForCommissionId = staffVO.getStaffGroupForCommission();
        if (staffGroupForCommissionId != null && staffGroupForCommissionId.longValue() > 0) {
            UserGroup ug = userGroupService.get(staffGroupForCommissionId);
            staff.getUserGroups().add(ug);
        }

        // staff payroll attribute
        StaffPayrollAttributeVO[] attributeVOs = staffVO.getStaffPayrollAttributeVOs();
        if (attributeVOs != null && attributeVOs.length > 0) {
            for (StaffPayrollAttributeVO attributeVO : attributeVOs) {
                if (StringUtils.isNotBlank(attributeVO.getValue())) {
                    StaffPayrollAttribute attribute = new StaffPayrollAttribute();
                    attribute.setStaff(staff);
                    attribute.setPayrollAttributeKey(payrollAttributeKeyService.get(attributeVO.getPayrollAttributeKeyId()));
                    attribute.setValue(attributeVO.getValue());
                    attribute.setIsActive(true);
                    attribute.setCreated(now);
                    attribute.setCreatedBy(loginUser);
                    attribute.setLastUpdated(now);
                    attribute.setLastUpdatedBy(loginUser);
                    staff.getStaffPayrollAttributes().add(attribute);
                }
            }
        }

        // 设置holiday
        StaffHoliday staffHoliday = staff.getStaffHoliday();
        if (staffHoliday == null) {
            staffHoliday = new StaffHoliday();
            staffHoliday.setCreated(now);
            staffHoliday.setCreatedBy(loginUser);
            staffHoliday.setUser(staff);
            staffHoliday.setIsActive(true);
        }
        staffHoliday.setAnnualLeave(staffVO.getAnnualLeave());
        staffHoliday.setSickLeave(staffVO.getSickLeave());
        staffHoliday.setLastUpdated(now);
        staffHoliday.setLastUpdatedBy(loginUser);
        staff.getStaffHolidays().add(staffHoliday);

        staff.setLastUpdated(now);
        staff.setLastUpdatedBy(loginUser);

        System.out.println("  RandomUtil.generateRandomNumberWithTime(staff.getUsername()) " + RandomUtil.generateRandomNumberWithTime(staff.getUsername()));

        String barcode = staffVO.getBarCode();
        if (StringUtils.isEmpty(barcode)) {
            barcode = RandomUtil.generateRandomNumber("S");
        }
        staff.setBarcode(barcode);

        staff.setPin(staffVO.getPin());

        saveOrUpdate(staff);

        // set staff home shops
        //初始化
        staffHomeShopDetailsService.init(staff.getId());
        if (homeShopIds != null && homeShopIds.length > 0) {
            Shop shop = null;
            for (Long shopId : homeShopIds) {
                shop = shopService.get(shopId);
                staff.getStaffHomeShops().add(shop);

                DetachedCriteria criteria = DetachedCriteria.forClass(StaffHomeShopDetails.class);
                criteria.add(Restrictions.eq("shop.id", shopId));
                criteria.add(Restrictions.eq("user.id", staff.getId()));
                StaffHomeShopDetails staffHomeShopDetails = staffHomeShopDetailsService.get(criteria);
                if (staffHomeShopDetails == null) {
                    StaffHomeShopDetails staffHomeShopDetails2 = new StaffHomeShopDetails();
                    staffHomeShopDetails2.setUser(staff);
                    staffHomeShopDetails2.setShop(shop);
                    staffHomeShopDetails2.setCreated(new Date());
                    staffHomeShopDetails2.setIsActive(true);
                    staffHomeShopDetails2.setSort(999999);
                    staffHomeShopDetails2.setCreatedBy(WebThreadLocal.getUser().getUsername());
                    staffHomeShopDetails2.setLastUpdated(new Date());
                    staffHomeShopDetails2.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
                    staffHomeShopDetailsService.saveOrUpdate(staffHomeShopDetails2);
                } else {
                    staffHomeShopDetails.setIsActive(true);
                    staffHomeShopDetails.setSort(999999);
                    staffHomeShopDetails.setLastUpdated(new Date());
                    staffHomeShopDetails.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
                    staffHomeShopDetailsService.saveOrUpdate(staffHomeShopDetails);
                }
            }
        }
    }

    public void setStaffCategory(Long[] categoryIds, User staff) {
        Date now = new Date();
        String loginUer = WebThreadLocal.getUser().getUsername();
        if (categoryIds == null || categoryIds.length == 0) {
            return;
        }
        Set<StaffTreatments> staffTreatmentsSet = staff.getStaffTreatmentses();
        for (Long categoryID : categoryIds) {
            if (staffTreatmentsSet.stream().filter(e -> e.getCategory() != null && e.getCategory().getId().equals(categoryID)).count() > 0) {
                // 已经包含该技能
                continue;
            }
            Category category = categoryService.get(categoryID);
            if (!category.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setCategory(category);
            staffTreatments.setStaff(staff);
            staffTreatments.setIsActive(true);
            staffTreatments.setCreated(now);
            staffTreatments.setCreatedBy(loginUer);
            staffTreatments.setLastUpdated(now);
            staffTreatments.setLastUpdatedBy(loginUer);
            staffTreatmentsSet.add(staffTreatments);
            if (category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), staff, staffTreatmentsSet);
            } else if (category.getProducts().size() > 0) {
                setStaffProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), staff);
            }
        }
    }

    public void saveTreatment(Set<Category> categories, User staff, Set<StaffTreatments> staffTreatmentsSet) {
        Date now = new Date();
        String loginUer = WebThreadLocal.getUser().getUsername();

        for (Category category : categories) {
            if (!category.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setCategory(category);
            staffTreatments.setStaff(staff);
            staffTreatments.setIsActive(true);
            staffTreatments.setCreated(now);
            staffTreatments.setCreatedBy(loginUer);
            staffTreatments.setLastUpdated(now);
            staffTreatments.setLastUpdatedBy(loginUer);
            staffTreatmentsSet.add(staffTreatments);
            if (category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), staff, staffTreatmentsSet);
            } else if (category.getProducts().size() > 0) {
                setStaffProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), staff);
            }
        }
    }

    public void setStaffProduct(Long[] productId, User staff) {
        if (productId == null || productId.length == 0) {
            return;
        }
       /* if (productId == null ) {
            // 清空 product
            Iterator<StaffTreatments> staffTreatmentsIterator = staff.getStaffTreatmentses().iterator();
            while (staffTreatmentsIterator.hasNext()) {
                StaffTreatments staffTreatments = staffTreatmentsIterator.next();
                if (staffTreatments.getProduct() != null) {
                    staffTreatmentsIterator.remove(); // 移除这个元素
                }
            }
            return;
        }*/
        Set<StaffTreatments> staffTreatmentsSet = staff.getStaffTreatmentses();
        for (Long productID : productId) {
            if (staffTreatmentsSet.stream().filter(e -> e.getProduct() != null && e.getProduct().getId().equals(productID)).count() > 0) {
                // 已经包含该技能
                continue;
            }
            Product product = productService.get(productID);
            if (!product.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setProduct(product);
            staffTreatments.setStaff(staff);
            staffTreatments.setIsActive(true);
            staffTreatments.setCreated(new Date());
            staffTreatments.setCreatedBy(WebThreadLocal.getUser().getUsername());
            staffTreatments.setLastUpdated(new Date());
            staffTreatments.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
            staffTreatmentsSet.add(staffTreatments);
        }
    }

    public List<User> getMemberListByShop(Long shopId, DetachedCriteria criteria) {
        if (criteria == null) {
            criteria = DetachedCriteria.forClass(User.class);
        }
        criteria.add(Restrictions.eq("shop.id", shopId));
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
        return list(criteria);
    }

    @Override
    public User saveMember(MemberAddVO memberAddVO) {
        return saveMember(memberAddVO, false);
    }

    @Override
    public User saveMember(MemberAddVO memberAddVO, Boolean register) {
        User member = new User();
        member.setEnabled(true);
        member.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        if (memberAddVO.getPassword() == null) {
            member.setPassword(EncryptUtil.SHA1(RandomStringUtils.randomAlphanumeric(8))); // 随机生成一个密码

        } else {
            member.setPassword(EncryptUtil.SHA1(memberAddVO.getPassword()));
        }
        /*        member.setPassword(EncryptUtil.SHA1(RandomStringUtils.randomAlphanumeric(8)));    // 随机生成一个密码*/
        member.setFirstName(memberAddVO.getFirstName());
        member.setLastName(StringUtils.isNotBlank(memberAddVO.getLastName()) ? memberAddVO.getLastName() : "N/A");
        member.setFullName(memberAddVO.getFirstName() + " " + memberAddVO.getLastName());

        member.setEmail(memberAddVO.getEmail());
        member.setGender(memberAddVO.getGender());
        member.setDateOfBirth(memberAddVO.getDateOfBirth());
        member.setPreferredContact(memberAddVO.getPreferredContact());
        member.setNotification(memberAddVO.getNotification());

        member.setOptedOut(memberAddVO.getOptedOut() != null ? memberAddVO.getOptedOut() : false);
        member.setHasMPF(false);

        member.getUserGroups().clear();

        Long[] usergroupsIds = memberAddVO.getUsergroupsIds();
        if (usergroupsIds != null && usergroupsIds.length > 0) {
            UserGroup ug = null;
            for (Long ugId : usergroupsIds) {
                ug = userGroupService.get(ugId);
                member.getUserGroups().add(ug);
            }
        }

        if (!register) {
            member.setActiving(true);
        }
        //member's role is null
        String preferredTherapistId = memberAddVO.getPreferredTherapistId();
        if (StringUtils.isNotBlank(preferredTherapistId)) {
            User therapist = get(Long.valueOf(preferredTherapistId));
            member.setUser(therapist);
        }

        /* create by william -- 2018-8-14 */
        String preferredTherapistId1 = memberAddVO.getPreferredTherapistId1();
        if (StringUtils.isNotBlank(preferredTherapistId1)) {
            User therapist1 = get(Long.valueOf(preferredTherapistId1));
            member.setUser(therapist1);
        }
        String preferredTherapistId2 = memberAddVO.getPreferredTherapistId2();
        if (StringUtils.isNotBlank(preferredTherapistId2)) {
            User therapist2 = get(Long.valueOf(preferredTherapistId2));
            member.setTherapist2(therapist2);
        }
        String preferredTherapistId3 = memberAddVO.getPreferredTherapistId3();
        if (StringUtils.isNotBlank(preferredTherapistId3)) {
            User therapist3 = get(Long.valueOf(preferredTherapistId3));
            member.setTherapist3(therapist3);
        }

        String preferredShop = memberAddVO.getPreferredShop();
        if (StringUtils.isNotBlank(preferredShop)) {
            Shop shop = shopService.get(Long.valueOf(preferredShop));
            member.setPreferredShop(shop);
        }

        String preferredRoom = memberAddVO.getPreferredRoom();
        if (StringUtils.isNotBlank(preferredRoom)) {
            Room room = roomService.get(Long.valueOf(preferredRoom));
            member.setPreferredRoom(room);
        } else {
            member.setPreferredRoom(null);
        }

        Shop shop = shopService.get(Long.valueOf(memberAddVO.getShopId()));
        member.setShop(shop);
        member.setRemarks(memberAddVO.getRemarks());
        member.setAirmilesMembershipNumber(memberAddVO.getAirmilesMembershipNumber());

        if (memberAddVO.getCommunicationChannelId() != null) {
            CommunicationChannel cc = communicationChannelService.get(memberAddVO.getCommunicationChannelId());
            member.getCommunicationChannels().add(cc);
        }

        member.setCreated(new Date());
        member.setCreatedBy(WebThreadLocal.getUser() != null ? WebThreadLocal.getUser().getUsername() : "Register");
        member.setLastUpdated(new Date());
        member.setLastUpdatedBy(WebThreadLocal.getUser() != null ? WebThreadLocal.getUser().getUsername() : "Register");
        if(StringUtils.isNotBlank(memberAddVO.getUsername())){
            member.setUsername(memberAddVO.getUsername());
        }else {
            member.setUsername(generateNextUsername(CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
        }
        save(member);

        if (StringUtils.isNotBlank(memberAddVO.getMobilePhone())) {
            Phone mPhone = new Phone();
            mPhone.setNumber(memberAddVO.getMobilePhone());
            mPhone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            mPhone.setUser(member);
            phoneService.save(mPhone);
        }
        if (StringUtils.isNotBlank(memberAddVO.getHomePhone())) {
            Phone mPhone = new Phone();
            mPhone.setNumber(memberAddVO.getHomePhone());
            mPhone.setType(CommonConstant.PHONE_TYPE_HOME);
            mPhone.setUser(member);
            phoneService.save(mPhone);
        }
        if (memberAddVO.getAddressVO() != null) {
            AddressVO addrvo = memberAddVO.getAddressVO();
            Address address = new Address();
            address.setAddressExtention(addrvo.getAddressExtention());
            address.setCity(addrvo.getCity());
            address.setCountry(addrvo.getCountry());
            address.setCompany(WebThreadLocal.getCompany());
            address.setDistrict(addrvo.getDistrict());
            address.setUser(member);
            address.setCreated(new Date());
            address.setCreatedBy(WebThreadLocal.getUser() != null ? WebThreadLocal.getUser().getUsername() : "Register");
            address.setIsActive(true);

            addressService.saveOrUpdate(address);
        }
        // saveOrUpdate basic loyalty level for member
        LoyaltyLevel ll = loyaltyLevelService.getActiveLoyaltyLevelByRank(1);
        UserLoyaltyLevelVO userLoyaltyLevelVO = new UserLoyaltyLevelVO();
        userLoyaltyLevelVO.setJoinDate(new Date());
        userLoyaltyLevelVO.setLoyaltyLevel(ll);
        userLoyaltyLevelVO.setUser(member);

        //earn spa points(because of basic level with 0 spa points)
        userLoyaltyLevelService.saveUserLoyaltyLevel(userLoyaltyLevelVO);

 /*      if(register){
        	// 生成注册激活的验证码
            UserCode userCode = userCodeService.saveActivatingCode(member);
            // saveBlock mailshot
            MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(member);
            // 通过email发送激活的链接
            RegistrationThread.getInstance(mktMailShot, member, userCode).start();
        }
        */
        // 发送设置密码的email
        ForgetPasswordVO forgetPasswordVO = new ForgetPasswordVO();
        forgetPasswordVO.setAccountType(member.getAccountType());
        forgetPasswordVO.setEmail(member.getEmail());
        newMembersSendEmail(forgetPasswordVO);
        return member;
    }

    @Override
    public void updateMember(MemberEditVO memberEditVO) {

        User member = get(Long.valueOf(memberEditVO.getId()));
        //member.setUsername(memberEditVO.getUsername());

        member.setFirstName(memberEditVO.getFirstName());
        member.setLastName(memberEditVO.getLastName());
        member.setFullName(memberEditVO.getFirstName() + " " + memberEditVO.getLastName());

        member.setEmail(memberEditVO.getEmail());
        member.setGender(memberEditVO.getGender());
        member.setEnabled(Boolean.valueOf(memberEditVO.getEnabled()));
        member.setOptedOut(memberEditVO.getOptedOut() != null ? memberEditVO.getOptedOut() : false);

        member.getUserGroups().clear();
        Long[] usergroupsIds = memberEditVO.getUsergroupsIds();
        if (usergroupsIds != null && usergroupsIds.length > 0) {
            UserGroup ug = null;
            for (Long ugId : usergroupsIds) {
                ug = userGroupService.get(ugId);
                member.getUserGroups().add(ug);
            }
        }

        member.setDateOfBirth(memberEditVO.getDateOfBirth());
        member.setPreferredContact(memberEditVO.getPreferredContact());
        if (memberEditVO.getNotification() != null && memberEditVO.getNotification().size() > 0) {
            member.setNotification(String.join(",", memberEditVO.getNotification()));
        } else {
            member.setNotification(null);
        }

        String preferredTherapistId = memberEditVO.getPreferredTherapistId();
        if (StringUtils.isNotBlank(preferredTherapistId)) {
            User therapist = get(Long.valueOf(preferredTherapistId));
            member.setUser(therapist);
        } else {
            member.setUser(null);
        }

        /* create by william -- 2018-8-14 */

        String preferredTherapistId1 = memberEditVO.getPreferredTherapistId1();
        if (StringUtils.isNotBlank(preferredTherapistId1)) {
            User therapist1 = get(Long.valueOf(preferredTherapistId1));
            member.setUser(therapist1);
        } else {
            member.setUser(null);
        }
        String preferredTherapistId2 = memberEditVO.getPreferredTherapistId2();
        if (StringUtils.isNotBlank(preferredTherapistId2)) {
            User therapist2 = get(Long.valueOf(preferredTherapistId2));
            member.setTherapist2(therapist2);
        } else {
            member.setTherapist2(null);
        }
        String preferredTherapistId3 = memberEditVO.getPreferredTherapistId3();
        if (StringUtils.isNotBlank(preferredTherapistId3)) {
            User therapist3 = get(Long.valueOf(preferredTherapistId3));
            member.setTherapist3(therapist3);
        } else {
            member.setTherapist3(null);
        }

        String preferredShop = memberEditVO.getPreferredShop();
        if (StringUtils.isNotBlank(preferredShop)) {
            Shop shop = shopService.get(Long.valueOf(preferredShop));
            member.setPreferredShop(shop);
        }

        String preferredRoom = memberEditVO.getPreferredRoom();
        if (StringUtils.isNotBlank(preferredRoom)) {
            Room room = roomService.get(Long.valueOf(preferredRoom));
            member.setPreferredRoom(room);
        } else {
            member.setPreferredRoom(null);
        }

        Shop shop = shopService.get(Long.valueOf(memberEditVO.getShopId()));
        member.setShop(shop);

        member.setRemarks(memberEditVO.getRemarks());
        member.setAirmilesMembershipNumber(memberEditVO.getAirmilesMembershipNumber());
        member.setPassword(memberEditVO.getPassword());
        member.setLastUpdated(new Date());
        member.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());

        if (memberEditVO.getCommunicationChannelId() != null) {
            member.getCommunicationChannels().clear();
            CommunicationChannel cc = communicationChannelService.get(memberEditVO.getCommunicationChannelId());
            member.getCommunicationChannels().add(cc);
        }

        saveOrUpdate(member);

        if (StringUtils.isNotBlank(memberEditVO.getMobilePhone())) {
            List<Phone> mpList = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_MOBILE);
            Phone mPhone = null;
            if (mpList != null && mpList.size() > 0) {
                mPhone = mpList.get(0);
            } else {
                mPhone = new Phone();
                mPhone.setUser(member);
                mPhone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            }
            mPhone.setNumber(memberEditVO.getMobilePhone());
            phoneService.save(mPhone);
        }
        if (StringUtils.isNotBlank(memberEditVO.getHomePhone())) {
            List<Phone> hpList = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_HOME);
            Phone hPhone = null;
            if (hpList != null && hpList.size() > 0) {
                hPhone = hpList.get(0);
            } else {
                hPhone = new Phone();
                hPhone.setUser(member);
                hPhone.setType(CommonConstant.PHONE_TYPE_HOME);
            }
            hPhone.setNumber(memberEditVO.getHomePhone());
            phoneService.save(hPhone);
        }
        if (memberEditVO.getAddressVO() != null) {
            AddressVO addrvo = memberEditVO.getAddressVO();
            Address address = null;
            if (member.getAddresses() != null && member.getAddresses().size() > 0) {
                address = member.getAddresses().iterator().next();
                address.setLastUpdated(new Date());
                address.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
            } else {
                address = new Address();
                address.setCompany(WebThreadLocal.getCompany());
                address.setUser(member);
                address.setCreated(new Date());
                address.setCreatedBy(WebThreadLocal.getUser().getUsername());
                address.setIsActive(true);
            }
            address.setAddressExtention(addrvo.getAddressExtention());
            address.setCity(addrvo.getCity());
            address.setCountry(addrvo.getCountry());
            address.setDistrict(addrvo.getDistrict());

            addressService.saveOrUpdate(address);
        }
    }

    /**
     * 获取店的所有therapist
     *
     * @param shopId
     * @param criteria
     * @return
     */
    public List<User> getTherapistByShop(Long shopId, DetachedCriteria criteria) {
        if (criteria == null) {
            criteria = DetachedCriteria.forClass(User.class);
        }
        criteria.createAlias("staffHomeShops", "shs");
        criteria.add(Restrictions.eq("shs.id", shopId));
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
        criteria.createAlias("sysRoles", "sr");
        criteria.add(Restrictions.in("sr.reference", new String[]{CommonConstant.STAFF_ROLE_REF_THERAPIST, CommonConstant.STAFF_ROLE_REF_SHOP_MANAGER_T, CommonConstant.STAFF_ROLE_REF_RECEPTION_T}));
        criteria.addOrder(Order.asc("joinDate"));
        return list(criteria);
    }

    /**
     * 获取所有可以做productOption的技师集合
     *
     * @param shopId
     * @param productOption
     * @return
     */
    public List<User> getTherapistsBySkill(Long shopId, ProductOption productOption, Boolean isOnline) {
        if (shopId == null) {
            throw new IllegalArgumentException("ShopID should not be null");
        }
        Product product = productOption.getProduct();
        List<Long> categoryIds = product.getAllCategoryIds();
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.createAlias("staffHomeShops", "shs");
        criteria.add(Restrictions.eq("shs.id", shopId));

        if (isOnline != null) {
            criteria.add(Restrictions.eq("showOnApps", isOnline));
        }

        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
        criteria.createAlias("sysRoles", "sr");
        criteria.add(Restrictions.in("sr.reference", new String[]{CommonConstant.STAFF_ROLE_REF_THERAPIST, CommonConstant.STAFF_ROLE_REF_RECEPTION_T, CommonConstant.STAFF_ROLE_REF_SHOP_MANAGER_T}));
        //criteria.add(Restrictions.eq("sr.reference", CommonConstant.STAFF_ROLE_REF_THERAPIST));
        criteria.createAlias("staffTreatmentses", "sts");
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.in("sts.category.id", categoryIds));
        disjunction.add(Restrictions.eq("sts.product.id", product.getId()));
        criteria.add(disjunction);
        criteria.add(Restrictions.eq("sts.isActive", true));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);    //设置ENTITY级的DISTINCT模式，根实体
        return list(criteria);
    }

    @Override
    public List<User> getAvailableTherapistList(Long shopId, ProductOption productOption, Date startTime, Date endTime, Boolean isOnline) {
        List<User> userList = getTherapistsBySkill(shopId, productOption, isOnline);
        List<BookItem> bookItemList = bookItemService.getBookItemList(shopId, startTime, endTime);
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            for (BookItem bookItem : bookItemList) {
                for (BookItemTherapist itemTherapist : bookItem.getBookItemTherapists()) {
                    if (userIterator.next().getId().equals(itemTherapist.getUser().getId())) {
                        userIterator.remove();
                    }
                }
            }
        }
        return userList;
    }

    /**
     * 检查 therapist是否有 productOption技能
     *
     * @param therapist
     * @param productOption
     * @return
     */
    public boolean checkTherapistSkill(User therapist, ProductOption productOption) {
        Product product = productOption.getProduct();
        List<Long> categoryIds = product.getAllCategoryIds();
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("id", therapist.getId()));
        criteria.add(Restrictions.eq("enabled", true));
        criteria.createAlias("staffTreatmentses", "sts");
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.in("sts.category.id", categoryIds));
        disjunction.add(Restrictions.eq("sts.product.id", product.getId()));
        criteria.add(disjunction);
        criteria.add(Restrictions.eq("sts.isActive", true));
        return getCount(criteria) > 0;
    }

    public Set<String> getSysRoles(String userName, String accountType) {
        User user = getLoginUserByAccountWithType(userName, accountType);
        Set<String> roles = user.getSysRoles().stream().filter(SysRole::isIsActive).map(SysRole::getName).collect(Collectors.toSet());
        logger.debug("user:{}, roles:{}", userName, roles);
        return roles;
    }

    public Set<String> getPermissions(String userName, String accountType) {
        User user = getLoginUserByAccountWithType(userName, accountType);
        Set<SysRole> roles = user.getSysRoles();
        Set<String> permissions = new HashSet<>();
        for (SysRole role : roles) {
            permissions.addAll(role.getSysResources().stream().map(SysResource::getPermission).collect(Collectors.toSet()));
        }
        //logger.debug("user:{}, permissions:{}", userName, permissions);
        return permissions;
    }

    @Override
    public String generateNextUsername(String accountType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("accountType", accountType));
        criteria.setProjection(Projections.max("id"));
        List<Long> res = listIds(criteria);

        Long nextUsername = 100000L;
        if (res.size() > 0 && res.get(0) != null) {
            User lastUser = get(res.get(0));
            try {
                nextUsername = Long.parseLong(lastUser.getUsername()) + 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                nextUsername = lastUser.getId() + 20000L;
            }

            User hasUser = getUserByUsername(nextUsername.toString(), accountType);
            while (hasUser != null) {
                nextUsername += 1;
                hasUser = getUserByUsername(nextUsername.toString(), accountType);
            }
        }

        return nextUsername.toString();
    }

    @Override
    public void saveResetPassword(ForgetPasswordVO forgetPasswordVO) {
        String accountType = forgetPasswordVO.getAccountType();
        if (StringUtils.isBlank(accountType)) {
            logger.error("must provide account type!");
            return;
        }
        String email = forgetPasswordVO.getEmail();
        User user = getUserByEmail(email, accountType);
        if (user == null) {
            logger.error("Not found by email:{} and account type:{}!", email, accountType);
            return;
        }

        // 生成注册激活的验证码
        UserCode userCode = userCodeService.saveResetPasswordCode(user);
        System.out.println("userCode For resetPassword:" + userCode.getCode());
        // saveBlock mailshot
        //MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(user);
        // 通过email发送激活的链接
        //RegistrationThread.getInstance(mktMailShot, user, userCode).start();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user", user);
        parameterMap.put("company", WebThreadLocal.getCompany());
        parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_RESET_PASSWORD);
        parameterMap.put("code", userCode.getCode());
        parameterMap.put("urlRoot", WebThreadLocal.getUrlRoot()); // url链接的前缀
        CommonSendEmailThread.getInstance(parameterMap).start();
    }

    @Override
    public void saveAppsResetPassword(AppsForgetPasswordVO forgetPasswordVO, String url) {
        String accountType = forgetPasswordVO.getAccountType();
        if (StringUtils.isBlank(accountType)) {
            logger.error("must provide account type!");
            return;
        }
        String email = forgetPasswordVO.getEmail();
        User user = getUserByEmail(email, accountType);
        if (user == null) {
            logger.error("Not found by email:{} and account type:{}!", email, accountType);
            return;
        }

        // 生成注册激活的验证码
        UserCode userCode = userCodeService.saveResetPasswordCode(user);
        System.out.println("userCode For resetPassword:" + userCode.getCode());
        // saveBlock mailshot
        //MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(user);
        // 通过email发送激活的链接
        //RegistrationThread.getInstance(mktMailShot, user, userCode).start();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user", user);
        parameterMap.put("company", WebThreadLocal.getCompany());
        parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_RESET_PASSWORD);
        parameterMap.put("code", userCode.getCode());
        parameterMap.put("urlRoot", WebThreadLocal.getUrlRoot()); // url链接的前缀
        CommonSendEmailThread.getInstance(parameterMap).start();
    }

    /*@Override
    public void saveAppsResetPassword(AppsForgetPasswordVO forgetPasswordVO, String url) {
        String accountType = forgetPasswordVO.getAccountType();
        if (StringUtils.isBlank(accountType)) {
            logger.error("must provide account type!");
            return;
        }
        String email = forgetPasswordVO.getEmail();
        User user = getUserByEmail(email, accountType);
        if (user == null) {
            logger.error("Not found by email:{} and account type:{}!", email, accountType);
            return;
        }

        // 生成注册激活的验证码
        UserCode userCode = userCodeService.saveResetPasswordCode(user);
        System.out.println("userCode For resetPassword:" + userCode.getCode());
        // saveBlock mailshot
        //MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(user);
        // 通过email发送激活的链接
        //RegistrationThread.getInstance(mktMailShot, user, userCode).start();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user", user);
        parameterMap.put("company", WebThreadLocal.getCompany());
        parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_RESET_PASSWORD);
        parameterMap.put("code", userCode.getCode());
        parameterMap.put("urlRoot", url); // url链接的前缀
      *//*  parameterMap.put("urlRoot", WebThreadLocal.getUrlRoot()); *//*// url链接的前缀
        CommonSendEmailThread.getInstance(parameterMap).start();
    }*/

    @Override
    public void newMembersSendEmail(ForgetPasswordVO forgetPasswordVO) {
        String accountType = forgetPasswordVO.getAccountType();
        if (StringUtils.isBlank(accountType)) {
            logger.error("must provide account type!");
            return;
        }
        String email = forgetPasswordVO.getEmail();
        User user = getUserByEmail(email, accountType);
        if (user == null) {
            logger.error("Not found by email:{} and account type:{}!", email, accountType);
            return;
        }
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user", user);
        parameterMap.put("company", WebThreadLocal.getCompany());
        parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_REGISTRATION);
        CommonSendEmailThread.getInstance(parameterMap).start();
    }

    @Override
    public String updatePassword(ChangePasswordVO changePasswordVO) {
        User currentUser = get(changePasswordVO.getUserId());
        //Hibernate.initialize(currentUser.getCompany());
        if (changePasswordVO.getCheckPassword()) {
            String oldPassword = changePasswordVO.getOldPassword();
            if (StringUtils.isBlank(oldPassword) || !currentUser.getPassword().equals(EncryptUtil.SHA1(oldPassword))) {
                return I18nUtil.getMessageKey("label.errors.old.password.invalid");
            }
        }

        if (changePasswordVO.getSendPasswordByEmail() != null && changePasswordVO.getSendPasswordByEmail()) {
            //通过email发送密码
//            password = RandomStringUtils.randomAlphanumeric(8);
//            System.out.println("init password:" + password + " for " + currentUser.getId());
//            Map<String, Object> parameterMap = new HashMap<>();
//            parameterMap.put("password", password);
//            Hibernate.initialize(currentUser.getCompany()); // 初始化company
//            parameterMap.put("user", currentUser);
//            parameterMap.put("company", WebThreadLocal.getCompany());
//            parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_INIT_PASSWORD);
//            CommonSendEmailThread.getInstance(parameterMap).start();
            // 生成注册激活的验证码
            UserCode userCode = userCodeService.saveResetPasswordCode(currentUser);

            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("user", currentUser);
            parameterMap.put("company", WebThreadLocal.getCompany());
            parameterMap.put("templateType", CommonConstant.MARKETING_EMAIL_TEMPLATE_TYPE_RESET_PASSWORD);
            parameterMap.put("code", userCode.getCode());
            parameterMap.put("urlRoot", WebThreadLocal.getUrlRoot()); // url链接的前缀
            CommonSendEmailThread.getInstance(parameterMap).start();
        } else {
            // 通过输入更新密码
            String password = changePasswordVO.getPassword();
            if (StringUtils.isBlank(password)) {
                return I18nUtil.getMessageKey("label.errors.password.required");
            }
            if (password.length() < 6 || password.length() > 18) {
                return I18nUtil.getMessageKey("label.errors.password.length") + " 6-18";
            }
            if (!password.equals(changePasswordVO.getConfirmPassword())) {
                return I18nUtil.getMessageKey("label.errors.password.not.same");
            }
            // 更新密码
            currentUser.setPassword(EncryptUtil.SHA1(password));
            currentUser.setLastUpdated(new Date());
            currentUser.setLastUpdatedBy(currentUser.getUsername());
            saveOrUpdate(currentUser);
        }
        return "";
    }

    @Override
    public void resetPassword(ResetPasswordVO resetPasswordVO) {
        String code = resetPasswordVO.getCode();
        if (StringUtils.isBlank(code)) {
            logger.error("Code must not be null or empty!");
            return;
        }
        UserCode userCode = userCodeService.getByCode(code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
        if (userCode == null) {
            logger.error("Can not found code:{} in type:{}!", code, CommonConstant.USER_CODE_TYPE_RESET_PASSWORD);
            return;
        }
        // 重置密码
        User user = userCode.getUser();
        Date now = new Date();
        String userName = user.getUsername();
        user.setPassword(EncryptUtil.SHA1(resetPasswordVO.getPassword()));
        user.setLastUpdated(now);
        user.setLastUpdatedBy(userName);
        saveOrUpdate(user);

        userCode.setExpireDate(now); // 使过期
        userCodeService.saveOrUpdate(userCode);
    }


    public void saveRegister(RegisterVO registerVO) {
        Date now = new Date();
        User user = new User();
        String userName = "BY_USER_REGISTER";
        user.setCompany(registerVO.getCompany());
        user.setShop(shopService.get(registerVO.getShopId()));
        user.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        user.setPassword(EncryptUtil.SHA1(registerVO.getPassword()));
        user.setEmail(registerVO.getEmail());
        user.setFirstName(registerVO.getFirstName());
        user.setLastName(registerVO.getLastName());
        user.setJoinDate(now);
        user.setActiving(false);

        user.setEnabled(true);
        user.setCreated(now);
        user.setCreatedBy(userName);
        user.setLastUpdated(now);
        user.setLastUpdatedBy(userName);
        // 保存之前生成username
        user.setUsername(generateNextUsername(CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
        save(user);
        // 生成注册激活的验证码
        UserCode userCode = userCodeService.saveActivatingCode(user);
        // saveBlock mailshot
        MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(user);
        // 通过email发送激活的链接
        RegistrationThread.getInstance(mktMailShot, user, userCode).start();
    }

    @Override
    public List<MemberImportJxlsBean> importMember(ImportDemoVO importDemoVO) {
//        System.out.println("----importMember----run-----");
        List<MemberImportJxlsBean> memberImportJxlsBeanList = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("memberImportJxlsBeanList", memberImportJxlsBeanList);
        try {
            InputStream is = importDemoVO.getImportFile().getInputStream();
            ExcelUtil.read(is, "memberImportConfig.xml", beans);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (memberImportJxlsBeanList.size() == 0) {
            return null;
        }

        return saveMemberImport(memberImportJxlsBeanList);
    }

    /**
     * 保存会员信息
     *
     * @param memberImportJxlsBeanList
     * @return
     */
    private List<MemberImportJxlsBean> saveMemberImport(List<MemberImportJxlsBean> memberImportJxlsBeanList) {

        //创建导入失败的集合
        List<MemberImportJxlsBean> errorRecords = CollectionUtils.getLightWeightList();

        Shop shop;
        Date dateOfBirth;
        for (MemberImportJxlsBean bean : memberImportJxlsBeanList) {
            //验证shopName;
            if (StringUtils.isBlank(bean.getShopName())) {
                bean.setReturnError("ShopName can not be null");
                errorRecords.add(bean);
                continue;
            } else {
                shop = getShopByName(bean.getShopName().trim());
                if (shop == null) {
                    bean.setReturnError("Shop is not be exist");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证userName;
            String username;
            if (StringUtils.isBlank(bean.getUsername())) {
                username = generateNextUsername(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
            } else {
                username = bean.getUsername().trim().replaceAll(" +","");
                User existMember = getUserByUsername(username, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
                if (existMember != null) {
                    bean.setReturnError("Username is existed in system!");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证密码
            String password;
            if (StringUtils.isBlank(bean.getPassword())) {
                password = RandomUtil.generateRandomNumber("pw");
            }else{
                password = bean.getPassword().trim();
            }
            password = EncryptUtil.SHA1(password);
            //验证firstName
            if (StringUtils.isBlank(bean.getFirstName())) {
                bean.setReturnError("FirstName can not be null");
                errorRecords.add(bean);
                continue;
            } else {
                if (bean.getFirstName().length() > 40) {
                    bean.setReturnError("FirstName length do not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证lastName
            if (StringUtils.isNotBlank(bean.getLastName()) && bean.getLastName().length() > 40) {
                bean.setReturnError("LastName length do not more than 40");
                errorRecords.add(bean);
                continue;
            }
            //验证是否是email
            //验证是否邮箱
            String email = null;
            if(StringUtils.isNotBlank(bean.getEmail())){
                boolean b = isEmail(bean, errorRecords);
                if (b) {
                    continue;
                }
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
                detachedCriteria.add(Restrictions.eq("email",bean.getEmail()));
                detachedCriteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
                detachedCriteria.add(Restrictions.eq("enabled",true));
                List<User> list = list(detachedCriteria);
                if(list == null || list.size() == 0){
                   email = bean.getEmail();
                }else{
                    bean.setReturnError("Email is existed in system!");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                boolean flag = true;
                while(flag){
                    email = RandomUtil.generateRandomNumber2("IMS_");
                    email = email+"@imanagesystems.com";
                    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
                    detachedCriteria.add(Restrictions.eq("email",email));
                    detachedCriteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
                    detachedCriteria.add(Restrictions.eq("enabled",true));
                    List<User> list = list(detachedCriteria);
                    if(list == null || list.size() == 0){
                        flag = false;
                    }

                }


            }
            //验证性别
            if (StringUtils.isNotBlank(bean.getGender())) {
                if (!("MALE".equals(bean.getGender()) || "FEMALE".equals(bean.getGender()))) {
                    bean.setReturnError("Gender should be 'MALE' or 'FEMALE' ");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
               bean.setGender("FEMALE");
            }
            //验证手机号码
            String mobileNumber;
            if(StringUtils.isNotBlank(bean.getMobilePhone())){
                mobileNumber = MobileNumberUtils.verifyAddPrefixMobileNumber(bean.getMobilePhone());
                if (mobileNumber == null) {
                    bean.setReturnError("Mobile is not mobile number");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                mobileNumber = "";
            }
            if(StringUtils.isNotBlank(bean.getHomePhone())){
                //验证家庭号码
                String homeNumber = MobileNumberUtils.verifyHomePhoneNumber(bean.getHomePhone());
                if (homeNumber == null) {
                    bean.setReturnError("Home Phone is not home phone number");
                    errorRecords.add(bean);
                    continue;
                }
            }

            //验证国家
            if (StringUtils.isNotBlank(bean.getCountry())) {
                if (bean.getCountry().trim().length() > 40) {
                    bean.setReturnError("Country can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证省
            if (StringUtils.isNotBlank(bean.getProvince())) {
                if (bean.getProvince().trim().length() > 40) {
                    bean.setReturnError("Province can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证city
            if (StringUtils.isNotBlank(bean.getCity())) {
                if (bean.getCity().trim().length() > 40) {
                    bean.setReturnError("City can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证详细地址
            if (StringUtils.isNotBlank(bean.getAddress())) {
                if (bean.getAddress().trim().length() > 512) {
                    bean.setReturnError("Address length format error");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证生日
            if (bean.getDateOfBirth() != null) {
                try {
                    dateOfBirth = DateUtil.stringToDateThrowException(bean.getDateOfBirth(), "yyyy-MM-dd");
                } catch (ParseException e) {
                    bean.setReturnError("DateOfBirth format error");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                dateOfBirth = DateUtil.stringToDate("1980-01-01", "yyyy-MM-dd");
            }
            //验证status
            if (StringUtils.isNotBlank(bean.getNotification())) {
                if (!("ACTIVE".equalsIgnoreCase(bean.getNotification()) || "INACTIVE".equalsIgnoreCase(bean.getNotification()))) {
                    bean.setReturnError("Status should be 'ACTIVE' or 'INACTIVE'");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("Status can not empty");
                errorRecords.add(bean);
                continue;
            }
            MemberAddVO memberVO = new MemberAddVO();
            memberVO.setShopId(shop.getId() + "");
            memberVO.setUsername(username);
            memberVO.setPassword(password);
            memberVO.setFirstName(bean.getFirstName());
            memberVO.setLastName(bean.getLastName());
            memberVO.setEmail(email);
            memberVO.setGender(bean.getGender());
            memberVO.setMobilePhone(mobileNumber);
            memberVO.setHomePhone(bean.getHomePhone());
            AddressVO addressVO = new AddressVO();
            addressVO.setCountry(bean.getCountry());
            if (StringUtils.isBlank(bean.getCity())) {
                addressVO.setCity("Hong Kong");
                addressVO.setDistrict("Hong Kong");
            } else {
                addressVO.setCity(bean.getCity());
                addressVO.setDistrict(bean.getCity());
            }
            addressVO.setAddressExtention(bean.getAddress());
            memberVO.setAddressVO(addressVO);
            memberVO.setDateOfBirth(dateOfBirth);
            memberVO.setNotification(bean.getNotification());
            //插入数据
            saveMember(memberVO);
        }
        return errorRecords;
    }

    private boolean isStaffEmail(StaffImportJxlsBean bean, List<StaffImportJxlsBean> errorRecords) {
        boolean b = false;
        if (StringUtils.isNotBlank(bean.getEmail())) {
            String email = bean.getEmail();
            boolean matches = email.matches("\\w+@\\w+.com");
            if (!matches) {
                bean.setReturnError("email format should be XXX@XXX.com");
                errorRecords.add(bean);
                b = true;
                return b;
            }
        } else {
            bean.setReturnError("email can not be empty");
            errorRecords.add(bean);
            b = true;
            return b;
        }
        return b;
    }

    private boolean isEmail(MemberImportJxlsBean bean, List<MemberImportJxlsBean> errorRecords) {
        boolean b = false;
        if (StringUtils.isNotBlank(bean.getEmail())) {
            String email = bean.getEmail();
            boolean matches = email.matches("\\w+@\\w+.com");
            if (!matches) {
                bean.setReturnError("email format should be XXX@XXX.com");
                errorRecords.add(bean);
                b = true;
                return b;
            }
        } else {
            bean.setReturnError("email can not be empty");
            errorRecords.add(bean);
            b = true;
            return b;
        }
        return b;
    }

    @Override
    public List<StaffImportJxlsBean> importStaff(ImportDemoVO importDemoVO) {
        List<StaffImportJxlsBean> staffImportJxlsBeanList = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("staffImportJxlsBeanList", staffImportJxlsBeanList);
        try {
            InputStream is = importDemoVO.getImportFile().getInputStream();
            ExcelUtil.read(is, "staffImportConfig.xml", beans);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (staffImportJxlsBeanList.size() == 0) {
            return null;
        }
        return verifyAndSaveStaff(staffImportJxlsBeanList);
    }

    /**
     * 验证用户数据并保存
     *
     * @param
     * @return
     */
    private Shop getShopByName(String name){
        DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("name",name));
        List<Shop> shops = shopService.list(criteria);
        if(shops !=null && shops.size()>0){
            return shops.get(0);
        }
        return null;
    }
    private List<StaffImportJxlsBean> verifyAndSaveStaff(List<StaffImportJxlsBean> staffImportJxlsBeanList) {
        //错误的数据封装
        List<StaffImportJxlsBean> errorRecords = CollectionUtils.getLightWeightList();

        for (StaffImportJxlsBean bean : staffImportJxlsBeanList) {

            Long[] homeShopId;
            //验证homeshop
            if (StringUtils.isNotBlank(bean.getHomeShop())) {
                String[] split = bean.getHomeShop().trim().split("/");
                homeShopId = new Long[split.length];
                boolean exist = true;
                for (int i = 0; i < split.length; i++) {
                    Shop shop = getShopByName(split[i].trim());
                    if (shop != null ) {
                        homeShopId[i] = shop.getId();
                    } else {
                        bean.setReturnError("Home Shop [" + split[i] + "] can not be empty");
                        errorRecords.add(bean);
                        exist = false;
                        break;
                    }
                }
                if (!exist) {
                    continue;
                }
            } else {
                bean.setReturnError("Home Shop can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证username
            if (StringUtils.isNotBlank(bean.getUsername())) {
                User user = get("username", bean.getUsername().trim());
                if (user != null) {
                    bean.setReturnError("Username is exist");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("Username can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证password
            if (StringUtils.isBlank(bean.getPassword())) {
                bean.setReturnError("Password can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证firstName
            if (StringUtils.isNotBlank(bean.getFirstName())) {
                if (bean.getFirstName().trim().length() > 40) {
                    bean.setReturnError("FirstName length can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("firstName can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证LastName
            if (StringUtils.isNotBlank(bean.getLastName())) {
                if (bean.getLastName().trim().length() > 40) {
                    bean.setReturnError("LastName length can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("LastName can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证displayName
            if (StringUtils.isNotBlank(bean.getDisplayName())) {
                if (bean.getDisplayName().trim().length() > 40) {
                    bean.setReturnError("DisplayName length can not more than 40");
                    errorRecords.add(bean);
                    continue;
                }
            }
            //验证邮箱
            boolean b = isStaffEmail(bean, errorRecords);
            if (b) {
                continue;
            }
            User member = getUserByEmail(bean.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
            if (member != null) {
                bean.setReturnError("Email is existed in system!");
                errorRecords.add(bean);
                continue;
            }
            //验证手机
            String mobileNumber = MobileNumberUtils.verifyAddPrefixMobileNumber(bean.getMobile());
            if (mobileNumber == null) {
                bean.setReturnError("Mobile is not mobile number");
                errorRecords.add(bean);
                continue;
            }
            //验证roletype
            SysRole role;
            if (StringUtils.isNotBlank(bean.getRoleType())) {
                if (bean.getRoleType().equals(admin)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(therapist)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(shopManager)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(receptionist)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(operationalManager)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(marketing)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(shopMangerT)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(receptionT)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else if (bean.getRoleType().equals(liveChat)) {
                    role = sysRoleService.get("reference", bean.getRoleType());
                } else {
                    bean.setReturnError("AccountType is error");
                    errorRecords.add(bean);
                    continue;
                }
                if (role == null) {
                    bean.setReturnError("AccountType is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("AccountType is error");
                errorRecords.add(bean);
                continue;
            }
            //验证dob
            Date dob;
            try {
                if(bean.getDob() == null){
                    dob = DateUtil.stringToDateThrowException("1980-01-01", "yyyy-MM-dd");
                }else {
                    dob = bean.getDob();
                }
            } catch (Exception e) {
                bean.setReturnError("Dob should be yyyy-MM-dd");
                errorRecords.add(bean);
                continue;
            }
            //验证gender
            if (StringUtils.isNotBlank(bean.getGender())) {
                if (!("FEMALE".equals(bean.getGender()) || "MALE".equals(bean.getGender()))) {
                    bean.setReturnError("Gender should be 'FEMALE' or 'MALE'");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("Gender can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证pin
            if (StringUtils.isNotBlank(bean.getPin())) {
                if (bean.getPin().trim().length() != 4) {
                    bean.setReturnError("Pin must be 4 digits");
                    errorRecords.add(bean);
                    continue;
                }
                try {
                    Integer.valueOf(bean.getPin());
                    User user = get("pin", bean.getPin());
                    if (user != null && user.isEnabled()) {
                        bean.setReturnError("Pin is exist");
                        errorRecords.add(bean);
                        continue;
                    }
                } catch (Exception e) {
                    bean.setReturnError("Pin must be 4 digits");
                    errorRecords.add(bean);
                    continue;
                }

            } else {
                bean.setReturnError("Pin can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证staffnumber
            String barcode;
            if (StringUtils.isNotBlank(bean.getStaffNumber())) {
                User user = get("barcode", bean.getStaffNumber());
                if (user != null && user.isEnabled()) {
                    bean.setReturnError("Staff Number is exist");
                    errorRecords.add(bean);
                    continue;
                }
                barcode = bean.getStaffNumber();
            } else {
                barcode = RandomUtil.generateRandomNumber("S");
            }
            //验证joinDate
            Date joinDate;
            if (bean.getJoinDate() != null) {
                try {
                    joinDate = bean.getJoinDate();
                } catch (Exception e) {
                    bean.setReturnError("Join Date format should be yyyy-MM-dd");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                joinDate = new Date();
            }
            //验证employmentType
            PayrollTemplate payrollTemplate;
            if (StringUtils.isNotBlank(bean.getEmploymentType())) {
                if (!("FULL_TIME".equals(bean.getEmploymentType()) || "PART_TIME".equals(bean.getEmploymentType()))) {
                    bean.setReturnError("Employment Type should be 'FULL_TIME' or 'PART_TIME'");
                    errorRecords.add(bean);
                    continue;
                }
                payrollTemplate = payrollTemplateService.get("name", "FULL_TIME");
                if(payrollTemplate == null){
                    bean.setReturnError("Employment Type is not exist");
                    errorRecords.add(bean);
                    continue;
                }
            } else {
                bean.setReturnError("Employment can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证standardSalary，goodsTarget，treatmentTarget
            StaffPayrollAttributeVO[] vos = new StaffPayrollAttributeVO[3];
            if ("FULL_TIME".equals(bean.getEmploymentType())) {
                try{
                    Double.valueOf(bean.getStandardSalary());
                    Double.valueOf(bean.getGoodsTarget());
                    Double.valueOf(bean.getTreatmentTarget());
                }catch (Exception e){
                    bean.setReturnError("'StandardSalary' and 'Goods Target' and 'Treatment Target' must be digits");
                    errorRecords.add(bean);
                    continue;
                }
                PayrollAttributeKey standardSalary = payrollAttributeKeyService.get("reference", "STANDARD_SALARY");

                PayrollAttributeKey treatmentTarget = payrollAttributeKeyService.get("reference", "TARGET_AMOUNT_CA-GOODS");

                PayrollAttributeKey goodsTarget = payrollAttributeKeyService.get("reference", "TARGET_AMOUNT_CA-TREATMENT");

                Long standardSalaryKeyId = standardSalary.getId();
                Long treatmentTargetKeyId = treatmentTarget.getId();
                Long goodsTargetKeyId = goodsTarget.getId();

                StaffPayrollAttributeVO vo1 =new StaffPayrollAttributeVO();
                vo1.setPayrollAttributeKeyId(treatmentTargetKeyId);
                vo1.setReference(treatmentTarget.getReference());
                vo1.setName(treatmentTarget.getName());
                vo1.setValue(bean.getStandardSalary());
                vos[0]=vo1;

                StaffPayrollAttributeVO vo2 =new StaffPayrollAttributeVO();
                vo2.setPayrollAttributeKeyId(standardSalaryKeyId);
                vo2.setReference(standardSalary.getReference());
                vo2.setName(standardSalary.getName());
                vo2.setValue(bean.getGoodsTarget());
                vos[1]=vo2;

                StaffPayrollAttributeVO vo3 =new StaffPayrollAttributeVO();
                vo3.setPayrollAttributeKeyId(goodsTargetKeyId);
                vo3.setReference(goodsTarget.getReference());
                vo3.setName(goodsTarget.getName());
                vo3.setValue(bean.getTreatmentTarget());
                vos[2]=vo3;
            }
            //验证commissionGroup
            UserGroup userGroup;
            if(StringUtils.isNotBlank(bean.getCommissionGroup())){
                if(!("Supervisor".equals(bean.getCommissionGroup()) || "Senior Therapist".equals(bean.getCommissionGroup())
                    || "Therapist".equals(bean.getCommissionGroup()) || "GROs".equals(bean.getCommissionGroup()))){
                    bean.setReturnError("Commission Group should be 'Supervisor' or 'Senior Therapist' or 'Therapist' or 'GROs'");
                    errorRecords.add(bean);
                    continue;
                }
                DetachedCriteria ugdc = DetachedCriteria.forClass(UserGroup.class);
                ugdc.add(Restrictions.eq("isActive", true));
                ugdc.add(Restrictions.eq("type", "STAFF"));
                ugdc.add(Restrictions.eq("module", "COMMISSION"));
                List<UserGroup> ugList=userGroupService.list(ugdc);
                if(ugList == null || ugList.size()==0){
                    bean.setReturnError("Commission Group is not exist");
                    errorRecords.add(bean);
                    continue;
                }
                userGroup = ugList.get(0);
            }else{
                bean.setReturnError("Commission Group can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证allowonlinebooking
            if(StringUtils.isNotBlank(bean.getAllowOnlineBooking())){
                Boolean isAllowed = Boolean.parseBoolean(bean.getAllowOnlineBooking().trim());
                if(isAllowed ==null){
                    bean.setReturnError("Allowed online booking should be 'TRUE' or 'FALSE'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Allowed online booking can not be empty");
                errorRecords.add(bean);
                continue;
            }
            //验证status
            if(StringUtils.isNotBlank(bean.getStatus())){
                if(!("ACTIVE".equalsIgnoreCase(bean.getStatus()) || "INACTIVE".equalsIgnoreCase(bean.getStatus()))){
                    bean.setReturnError("Status should be 'ACTIVE' or 'INACTIVE'");
                    errorRecords.add(bean);
                    continue;
                }
            }else{
                bean.setReturnError("Status can not be empty");
                errorRecords.add(bean);
                continue;
            }

            StaffVO staffVO = new StaffVO();
            staffVO.setHomeShopIds(homeShopId);
            staffVO.setUsername(bean.getUsername());
            staffVO.setPassword(bean.getPassword());
            staffVO.setConfirmPassword(bean.getPassword());
            staffVO.setFirstName(bean.getFirstName());
            staffVO.setLastName(bean.getLastName());
            staffVO.setDisplayName(bean.getDisplayName());
            staffVO.setEmail(bean.getEmail());
            staffVO.setMobilePhone(bean.getMobile());
            staffVO.setSysRoleId(role.getId());
            staffVO.setDateOfBirth(dob);
            staffVO.setGender(bean.getGender());
            staffVO.setPin(bean.getPin());
            staffVO.setBarCode(barcode);
            staffVO.setJoinDate(joinDate);
            staffVO.setPayrollTemplateId(payrollTemplate.getId());
            staffVO.setStaffPayrollAttributeVOs(vos);
            staffVO.setStaffGroupForCommission(userGroup.getId());
            staffVO.setShowOnApps(Boolean.valueOf(bean.getAllowOnlineBooking()));
            if(bean.getStatus().equals("ACTIVE")){
                staffVO.setEnabled(true);
            }else{
                staffVO.setEnabled(false);
            }
            saveOrUpdateStaff(staffVO);
        }
        return errorRecords;
    }

    /*private StaffPayrollAttribute getStaffPayrollAttribute(String commonConstant, User staff, StaffImportJxlsBean bean,
                                                           Date now, String loginUser) {
        PayrollAttributeKey pak = payrollAttributeKeyService.get("reference", commonConstant);
        StaffPayrollAttribute attribute = new StaffPayrollAttribute();
        attribute.setStaff(staff);
        attribute.setPayrollAttributeKey(pak);
        attribute.setValue(bean.getStandarSalary());
        attribute.setIsActive(true);
        attribute.setCreated(now);
        attribute.setCreatedBy(loginUser);
        attribute.setLastUpdated(now);
        attribute.setLastUpdatedBy(loginUser);
        staff.getStaffPayrollAttributes().add(attribute);
        return attribute;
    }*/

    @Override
    public void enable(Long userId) {
        if (userId == null) {
            return;
        }
        User user = get(userId);
        user.setEnabled(true);
        saveOrUpdate(user);
    }

    @Override
    public void disable(Long userId) {
        if (userId == null) {
            return;
        }
        User user = get(userId);
        user.setEnabled(false);
        saveOrUpdate(user);
    }

    @Override
    public boolean isMobileUsed(Long companyId, String mobileNumber) {
        return isMobileUsed(companyId, mobileNumber, null);
    }

    @Override
    public boolean isMobileUsed(Long companyId, String mobileNumber, Long excludeUserId) {
        if (StringUtils.isBlank(mobileNumber)) {
            return false;
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(Phone.class);
        criteria.add(Restrictions.eq("type", CommonConstant.PHONE_TYPE_MOBILE));
        criteria.add(Restrictions.eq("company.id", companyId));
        criteria.add(Restrictions.eq("number", mobileNumber));
        if (excludeUserId != null) {
            criteria.add(Restrictions.ne("user.id", excludeUserId));
        }
        return phoneService.getCount(criteria) > 0;
    }

    @Override
    public Double getNeedMoneyToGetNextLevel(Long userId) {
        Double needMoney = Double.valueOf(0.0D);
        Date now = new Date();
        String fromDate = "";
        try {
            fromDate = DateUtil.dateToString(now, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LoyaltyLevel currentLoyaltyLevel = userLoyaltyLevelService.getCurrentLLOfUser(userId);


        LoyaltyLevel nextLoyaltyLevel = loyaltyLevelService.getNextLoyaltyLevel(currentLoyaltyLevel);
        if (currentLoyaltyLevel.getRank() == 7 || currentLoyaltyLevel.getRank() == 6) {
            nextLoyaltyLevel = loyaltyLevelService.getActiveLoyaltyLevelByRank(6);//black diva
        }
        Double requiredAmount = new Double(nextLoyaltyLevel.getRequiredSpaPoints() != null ? nextLoyaltyLevel.getRequiredSpaPoints() : 0d);
        Double remainValue = prepaidDao.sumRemainValue(fromDate, CommonConstant.PREPAID_TYPE_CASH_PACKAGE, userId);

        Boolean userDiscount = Boolean.FALSE;
        UserLoyaltyLevel currentUserLL = userLoyaltyLevelService.getActiveUserLoyaltyLevelOfUser(userId);
        if (currentUserLL != null) {
            Date expiryDate = currentUserLL.getExpiryDate();
            if (expiryDate != null) {
                Integer monthsAfterExpiry = 1;
                //PropertiesUtil.getIntegerValueByName("MONTHS_TO_UPGRADE_BEFORE_EXPIRY");

                Calendar beforeMonth = new GregorianCalendar();
                beforeMonth.setTime(expiryDate);
                beforeMonth.add(2, -monthsAfterExpiry.intValue());

                if ((now.after(beforeMonth.getTime())) && (now.before(expiryDate))) {
                    userDiscount = Boolean.valueOf(true);
                }
            }

            if (userDiscount.booleanValue()) {
                requiredAmount = new Double(nextLoyaltyLevel.getDiscountRequiredSpaPoints() != null ? nextLoyaltyLevel.getDiscountRequiredSpaPoints() : 0d);
                ;
            }
            if (remainValue.doubleValue() < requiredAmount.doubleValue()) {
                needMoney = Double.valueOf(requiredAmount.doubleValue() - remainValue.doubleValue());
            }
        }

        return needMoney;
    }

    @Override
    public Double getNeedMoneyToRenewCurrentLevel(Long userId) {
        Double needMoney = Double.valueOf(0.0D);
        Date now = new Date();
        String fromDate = "";
        try {
            fromDate = DateUtil.dateToString(now, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LoyaltyLevel currentLoyaltyLevel = userLoyaltyLevelService.getCurrentLLOfUser(userId);

        if (currentLoyaltyLevel.getRank() == 7) {
            currentLoyaltyLevel = loyaltyLevelService.getActiveLoyaltyLevelByRank(6);//black diva
        }

        Double requiredAmount = new Double(currentLoyaltyLevel.getRequiredSpaPoints() != null ? currentLoyaltyLevel.getRequiredSpaPoints() : 0d);
        Double remainValue = prepaidDao.sumRemainValue(fromDate, CommonConstant.PREPAID_TYPE_CASH_PACKAGE, userId);

        UserLoyaltyLevel currentUserLL = userLoyaltyLevelService.getActiveUserLoyaltyLevelOfUser(userId);
        if (currentUserLL != null) {

            Boolean userDiscount = Boolean.FALSE;
            Date expiryDate = currentUserLL.getExpiryDate();
            if (expiryDate != null) {
                Integer monthsAfterExpiry = 1;

                Calendar beforeMonth = new GregorianCalendar();
                beforeMonth.setTime(expiryDate);
                beforeMonth.add(2, -monthsAfterExpiry.intValue());

                if ((now.after(beforeMonth.getTime())) && (now.before(expiryDate))) {
                    userDiscount = Boolean.valueOf(true);
                }
            }
            if (userDiscount.booleanValue()) {
                requiredAmount = new Double(currentLoyaltyLevel.getDiscountRequiredSpaPoints() != null ? currentLoyaltyLevel.getDiscountRequiredSpaPoints() : 0d);
                ;
            }

            if (remainValue.doubleValue() < requiredAmount.doubleValue()) {
                needMoney = Double.valueOf(requiredAmount.doubleValue() - remainValue.doubleValue());
            }
        }
        return needMoney;
    }

    @Override
    public Page<User> listMember(MemberAdvanceVO memberAdvanceVO) {
        return memberDao.getMemberList(memberAdvanceVO);
    }

    @Override
    public List<User> listAllMember(MemberAdvanceVO memberAdvanceVO) {
        return memberDao.getAllMemberList(memberAdvanceVO);
    }

    @Override
    public List<User> getStaffListByRole(String sysRoleReference) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.createAlias("sysRoles", "sr");
        criteria.add(Restrictions.eq("sr.reference", sysRoleReference));
        criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
        return list(criteria);
    }

    @Override
    public void saveServiceSettingsForStaff(StaffServiceSettingsVO staffServiceSettingsVO) {
        User staff = memberDao.get(staffServiceSettingsVO.getId());
        staff.getStaffTreatmentses().clear();
        setStaffCategory(staffServiceSettingsVO.getCategoryIds(), staff);
        setStaffProduct(staffServiceSettingsVO.getProductIds(), staff);
    }

    @Override
    public Map<String, String> getMemberDetailsImortToSFByCSV(Integer limitNum) {
        return getMemberDetailsImortToSFByCSV(limitNum, false);
    }

    @Override
    public Map<String, String> getMemberDetailsImortToSFByCSV(Integer limitNum, Boolean isDemo) {
        return getMemberDetailsImortToSFByCSV(limitNum, isDemo, null);
    }

    @Override
    public Map<String, String> getMemberDetailsImortToSFByCSV(Integer limitNum, Boolean isDemo, List<Object[]> searchResults) {

        Map<String, String> returnFiles = new HashMap<String, String>();

        List<Object> headAccountList = new ArrayList<Object>();
        headAccountList.add("SOT_Account_Number__c");
        headAccountList.add("Name");
        headAccountList.add("Registration_Location__c");
        headAccountList.add("Enabled__c");
        headAccountList.add("Active__c");
        headAccountList.add("Diva_Level__c");
        headAccountList.add("RecordTypeID");

        List<Object> headContactList = new ArrayList<Object>();
        headContactList.add("Account.SOT_Account_Number__c");
        headContactList.add("SOT_Account_Number_2__c");
        headContactList.add("FirstName");
        headContactList.add("LastName");
        headContactList.add("Gender__c");
        headContactList.add("Email");
        headContactList.add("MobilePhone");
        headContactList.add("HomePhone");
        headContactList.add("Phone");
        headContactList.add("Birthdate");
        headContactList.add("MailingCountry");
        headContactList.add("MailingCity");
        headContactList.add("MailingState");
        headContactList.add("MailingPostalCode");
        headContactList.add("MailingStreet");
        headContactList.add("Journey_Trigger_ID__c");
        headContactList.add("SOT__c");

        List<List<Object>> dataAccountList = new ArrayList<List<Object>>();
        List<List<Object>> dataContactList = new ArrayList<List<Object>>();

        List<Object> rowAccountList = null;
        List<Object> rowContactList = null;

        if (searchResults == null) {
            searchResults = memberDao.getMemberDetailsExportToCSVForSF(limitNum, isDemo);
        }
        if (searchResults == null || searchResults.size() <= 0) {
            return returnFiles;
        }

        logger.debug("member size by searching from database ----------- ::" + searchResults.size());

        try {
            int i = 1;
            for (Object[] obj : searchResults) {
                Long id = (Long) obj[0];
                User user = get(id);
                //contact
                rowContactList = new ArrayList<Object>();
                rowContactList.add(obj[1] != null ? (String) obj[1] : "");
                rowContactList.add(obj[1] != null ? (String) obj[1] : "");

                String firstName = (String) obj[4];
                if (StringUtils.isNotBlank(firstName)) {
                    rowContactList.add(firstName);
                } else {
                    rowContactList.add((String) obj[1]);
                }
                String lastName = (String) obj[5];
                if (StringUtils.isNotBlank(lastName)) {
                    rowContactList.add(lastName);
                } else {
                    rowContactList.add("N/A");
                }
                rowContactList.add(obj[6] != null ? (String) obj[6] : "");
                String email = (String) obj[7];
                if (!EmailUtils.checkEmailAddressFormat(email)) {
                    rowContactList.clear();
                    user.setLastModifier(new Date());
                    saveOrUpdate(user);
                    continue;
                }
                rowContactList.add(email);


                rowContactList.add(user.getMobilePhone() != null ? user.getMobilePhone() : "");
                rowContactList.add(user.getHomePhone() != null ? user.getHomePhone() : "");
                rowContactList.add(user.getBusinessPhone() != null ? user.getBusinessPhone() : "");
                Date birthday = (Date) obj[8];
                if (birthday != null) {
                    rowContactList.add(birthday);
                } else {
                    rowContactList.add("1979-01-01");
                }
                //address
                Set<Address> addrs = user.getAddresses();
                Address addr = null;
                if (addrs != null && addrs.size() > 0) {
                    addr = addrs.iterator().next();
                }
                rowContactList.add(addr != null && addr.getCountry() != null ? addr.getCountry() : "");
                if (addr != null) {
                    String city = addr.getCity();
                    if (StringUtils.isNotBlank(city)) {
                        if (city.length() > 5) {
                            rowContactList.add("HK");
                        } else {
                            rowContactList.add(city);
                        }
                    } else {
                        rowContactList.add("");
                    }
                } else {
                    rowContactList.add("");
                }

                if (addr != null) {
                    String state = addr.getDistrict();
                    if (StringUtils.isNotBlank(state)) {
                        if (state.length() > 30) {
                            rowContactList.add("Central");
                        } else {
                            rowContactList.add(state);
                        }
                    } else {
                        rowContactList.add("");
                    }
                } else {
                    rowContactList.add("");
                }
                rowContactList.add(addr != null && addr.getPostCode() != null ? addr.getPostCode() : "");
                if (addr != null) {
                    String address = addr.getAddressExtention();
                    if (StringUtils.isNotBlank(address)) {
                        rowContactList.add(address);
                    } else {
                        rowContactList.add("");
                    }
                } else {
                    rowContactList.add("");
                }
                String marketingCampaignSets = userMarketingCampaignTransactionService.getMarketingCampaignCodes(user.getId(), null, true);
                rowContactList.add(marketingCampaignSets);
                rowContactList.add("TRUE");
                dataContactList.add(rowContactList);

                //Account
                rowAccountList = new ArrayList<Object>();
                rowAccountList.add(obj[1] != null ? (String) obj[1] : "");

                String fulllname = (String) obj[2];
                if (StringUtils.isBlank(fulllname)) {
                    fulllname = (String) obj[1];
                }
                rowAccountList.add(fulllname);
                rowAccountList.add(obj[3] != null ? (String) obj[3] : "");
                rowAccountList.add("true");
                rowAccountList.add("true");
                rowAccountList.add(user.getCurrentLoyaltyLevel().getName() != null ? user.getCurrentLoyaltyLevel().getName() : "");
                rowAccountList.add(APIConstant.SF_RECORD_TYPE_ID_MEMBER);
                dataAccountList.add(rowAccountList);
                i++;
                if (i % 5 == 0) {
                    logger.debug("member processing number----" + i);
                }

            }
            logger.debug("member size by exporting to csv file  ::" + dataAccountList.size());
            String now = null;
            try {
                now = DateUtil.dateToString(new Date(), "yyyy MM dd HH mm ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String fileNameAccount = APIConstant.SSL_IMPORT_TO_SF_MEMBER + "_" + APIConstant.SF_OBJECT_ACCOUNT + "_" + now.replace(" ", "") + ".csv";
            String fileNameContact = APIConstant.SSL_IMPORT_TO_SF_MEMBER + "_" + APIConstant.SF_OBJECT_CONTACT + "_" + now.replace(" ", "") + ".csv";


            CSVUtils.writeCSV(headAccountList, dataAccountList, fileNameAccount, APIConstant.IMPORT_FILE_PATH);
            CSVUtils.writeCSV(headContactList, dataContactList, fileNameContact, APIConstant.IMPORT_FILE_PATH);

            returnFiles.put(APIConstant.SF_OBJECT_ACCOUNT, fileNameAccount);
            returnFiles.put(APIConstant.SF_OBJECT_CONTACT, fileNameContact);
            returnFiles.put("returnSize", dataAccountList.size() + "");
            return returnFiles;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFiles;
    }

    @Override
    public Results updateMemberDetailsByAPI(MemberAPIVO memberAPIVO) {
        Results results = Results.getInstance();

        String username = memberAPIVO.getUsername();
        String password = memberAPIVO.getPassword();
        User loginer = getUserByUsername(username, CommonConstant.USER_ACCOUNT_TYPE_STAFF);
        if (loginer == null || !loginer.getPassword().equals(EncryptUtil.SHA1(password))) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Account or password error!");
        } else {

            User member = getUserByUsernameV2(memberAPIVO.getAccountName(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
            if (member == null) {
                return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Username of member error!");
            }

            member.setLastSFUpdated(new Date());

            String objType = memberAPIVO.getObjectType();

            if (objType.equals("ACCOUNT")) {

                String acitving = memberAPIVO.getActive();
                String enabled = memberAPIVO.getEnabled();

                logger.debug("-----object type :" + objType + "--- activing from json----" + acitving + "---enabled from json---" + enabled);

                if (StringUtils.isNotBlank(memberAPIVO.getActive())) {
                    member.setActiving(Boolean.valueOf(memberAPIVO.getActive()));
                }
                if (StringUtils.isNotBlank(memberAPIVO.getEnabled())) {
                    member.setEnabled(Boolean.valueOf(memberAPIVO.getEnabled()));
                }
            }
            if (objType.equals("CONTACT")) {

                String email = memberAPIVO.getEmail();
                logger.debug("-----object type :" + objType + "--- email from json----" + email);

                // check email

                if (StringUtils.isNotBlank(memberAPIVO.getEmail())) {
                    if (!EmailUtils.checkEmailAddressFormat(email)) {
                        return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email format error!");
                    }
                    if (!member.getEmail().equals(email)) {
                        User memberCheckByEmail = getUserByEmail(email, CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
                        if (memberCheckByEmail != null) {
                            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email dumplicate!");
                        } else {
                            member.setEmail(email);
                        }
                    }
                } else {
                    return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Email can't be empty!");
                }

                //
                member.setFirstName(StringUtils.isNotBlank(memberAPIVO.getFirstName()) ? memberAPIVO.getFirstName() : "");
                member.setLastName(StringUtils.isNotBlank(memberAPIVO.getLastName()) ? memberAPIVO.getLastName() : "");
                if (StringUtils.isNotBlank(memberAPIVO.getBirthdate())) {
                    Date birthday = DateUtil.stringToDate(memberAPIVO.getBirthdate(), "yyyy-MM-dd");
                    member.setDateOfBirth(birthday);
                }
                if (StringUtils.isNotBlank(memberAPIVO.getGender())) {
                    if (memberAPIVO.getGender().equals("Girl")) {
                        member.setGender("FEMALE");
                    } else if (memberAPIVO.getGender().equals("Boy")) {
                        member.setGender("MALE");
                    } else if (memberAPIVO.getGender().equals("Male")) {
                        member.setGender("MALE");
                    } else {
                        member.setGender("FEMALE");
                    }
                } else {
                    member.setGender("FEMALE");
                }


                //check mobile phone
                if (StringUtils.isNotBlank(memberAPIVO.getMobilePhone()) && !NumberUtil.isNumeric(memberAPIVO.getMobilePhone())) {
                    return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone format error!");
                }
                if (StringUtils.isNotBlank(memberAPIVO.getMobilePhone())) {
                    member.getPhones().clear();
                    Phone mobile = null;
                    List<Phone> mobiles = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_MOBILE);
                    if (mobiles != null && mobiles.size() > 0) {
                        mobile = mobiles.get(0);
                    } else {
                        mobile = new Phone();
                        mobile.setType(CommonConstant.PHONE_TYPE_MOBILE);
                        mobile.setUser(member);
                    }
                    mobile.setNumber(memberAPIVO.getMobilePhone());
                    member.getPhones().add(mobile);
                } else {
                    return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Mobile phone can't be empty!");
                }
                //check home phone
                if (StringUtils.isNotBlank(memberAPIVO.getHomePhone()) && !NumberUtil.isNumeric(memberAPIVO.getHomePhone())) {
                    return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Home phone format error!");
                }
                if (StringUtils.isNotBlank(memberAPIVO.getHomePhone())) {
                    Phone homePhone = null;
                    List<Phone> homephones = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_HOME);
                    if (homephones != null && homephones.size() > 0) {
                        homePhone = homephones.get(0);
                    } else {
                        homePhone = new Phone();
                        homePhone.setType(CommonConstant.PHONE_TYPE_HOME);
                        homePhone.setUser(member);
                    }
                    homePhone.setNumber(memberAPIVO.getHomePhone());
                    member.getPhones().add(homePhone);
                }

                //check business phone
                if (StringUtils.isNotBlank(memberAPIVO.getPhone()) && !NumberUtil.isNumeric(memberAPIVO.getPhone())) {
                    return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Phone format error!");
                }
                if (StringUtils.isNotBlank(memberAPIVO.getPhone())) {
                    Phone phone = null;
                    List<Phone> phones = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_BUSINESS);
                    if (phones != null && phones.size() > 0) {
                        phone = phones.get(0);
                    } else {
                        phone = new Phone();
                        phone.setType(CommonConstant.PHONE_TYPE_BUSINESS);
                        phone.setUser(member);
                    }
                    phone.setNumber(memberAPIVO.getPhone());
                    member.getPhones().add(phone);
                }

                Address address = null;
                Set<Address> addressSet = member.getAddresses();
                if (addressSet != null && addressSet.size() > 0) {
                    address = addressSet.iterator().next();
                } else {
                    address = new Address();
                    address.setCreated(new Date());
                    address.setCreatedBy("SF_API");
                    address.setIsActive(true);
                    address.setUser(member);
                }

                address.setCountry(StringUtils.isNotBlank(memberAPIVO.getMailingCountry()) ? memberAPIVO.getMailingCountry() : "");
                address.setCity(StringUtils.isNotBlank(memberAPIVO.getMailingCity()) ? memberAPIVO.getMailingCity() : "");
                address.setPostCode(StringUtils.isNotBlank(memberAPIVO.getMailingPostalCode()) ? memberAPIVO.getMailingPostalCode() : "");
                address.setDistrict(StringUtils.isNoneBlank(memberAPIVO.getMailingState()) ? memberAPIVO.getMailingState() : "Unknown");
                address.setAddressExtention(StringUtils.isNotBlank(memberAPIVO.getMailingStreet()) ? memberAPIVO.getMailingStreet() : "");

                address.setLastUpdatedBy("SF_API");
                address.setLastUpdated(new Date());
                addressService.saveOrUpdate(address);
            }

            logger.debug("before save member details to ssl----------object type :" + objType + ",enabled:" + member.isEnabled() + ",email:" + member.getEmail() + ",first name:" + member.getFirstName()
                    + ",last name:" + member.getLastName() + ",mobile:" + member.getMobilePhone() + ",birthday:" + member.getDateOfBirth() + ",gender:" + member.getGender() + "-----object type :" + objType);

            saveOrUpdate(member);
            User memberFromDB = getUserByUsernameV2(memberAPIVO.getAccountName(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);

            logger.debug("after save member details from DB----------object type :" + objType + ",enabled:" + member.isEnabled() + ",email:" + memberFromDB.getEmail() + ",first name:" + memberFromDB.getFirstName()
                    + ",last name:" + memberFromDB.getLastName() + ",mobile:" + memberFromDB.getMobilePhone() + ",birthday:" + memberFromDB.getDateOfBirth() + ",gender:" + memberFromDB.getGender() + "-----object type :" + objType);

            return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "Updated successfully!");
        }
    }

    @Override
    public List<Object[]> getMemberDetailsExportToCSVForSF(Integer limitNum) {
        return memberDao.getMemberDetailsExportToCSVForSF(limitNum, Boolean.FALSE);
    }

    @Override
    public List<Object[]> getMemberDetailsExportToCSVForSF(Integer limitNum, Boolean isDemo) {
        return memberDao.getMemberDetailsExportToCSVForSF(limitNum, isDemo);
    }

    private User getUserByUsernameV2(String username, String accountType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("accountType", accountType));
        criteria.add(Restrictions.eq("username", username));
        return get(criteria);
    }

    @Override
    public void updateUserSetLastModifier(String date, Boolean demoRecords) {
        memberDao.updateUserSetLastModifier(date, demoRecords);
    }

    @Override
    public List<User> getUsersByBirthdayMonth(Integer month) {
        return memberDao.getUsersByBirthdayMonth(month);
    }

    @Override
    public List<Long> getMemberByNotInIds(List<Long> ids) {
        return memberDao.getMemberIdsNotIn(ids);
    }

    @Override
    public Double getRemainValueOfCashPackage(Long userId) {
        Date now = new Date();
        String fromDate = "";
        try {
            fromDate = DateUtil.dateToString(now, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return prepaidDao.sumRemainValue(fromDate, CommonConstant.PREPAID_TYPE_CASH_PACKAGE, userId);
    }

    @Override
    public void updateMobilePhone(AppsMobilePhoneEditVO mobilePhoneEditVO) {
        User member = get(Long.valueOf(mobilePhoneEditVO.getId()));
        member.setNotification(mobilePhoneEditVO.getNotification());
        member.setOptedOut(mobilePhoneEditVO.getOptedOut());
        member.setLastUpdated(new Date());
        /*        member.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());*/
        saveOrUpdate(member);
        if (StringUtils.isNotBlank(mobilePhoneEditVO.getMobilePhone())) {
            List<Phone> mpList = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_MOBILE);
            Phone mPhone = null;
            if (mpList != null && mpList.size() > 0) {
                mPhone = mpList.get(0);
            } else {
                mPhone = new Phone();
                mPhone.setUser(member);
                mPhone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            }
            mPhone.setNumber(mobilePhoneEditVO.getMobilePhone());

            phoneService.save(mPhone);
        }
      /*  if (StringUtils.isNotBlank(memberEditVO.getHomePhone())) {
            List<Phone> hpList = phoneService.getPhonesByUserIdAndType(member.getId(), CommonConstant.PHONE_TYPE_HOME);
            Phone hPhone = null;
            if (hpList != null && hpList.size() > 0) {
                hPhone = hpList.get(0);
            } else {
                hPhone = new Phone();
                hPhone.setUser(member);
                hPhone.setType(CommonConstant.PHONE_TYPE_HOME);
            }
            hPhone.setNumber(memberEditVO.getHomePhone());
            phoneService.save(hPhone);
        }*/

    }

    @Override
    public String updateAppsMemberPassword(ChangeAppsPasswordVO changePasswordVO) {
        User currentUser = get(changePasswordVO.getUserId());
        // 通过输入更新密码
        String password = changePasswordVO.getPassword();
	         /*   if (StringUtils.isBlank(password)) {
	                return I18nUtil.getMessageKey("label.errors.password.required");
	            }
	            if (password.length() < 6 || password.length() > 18) {
	                return I18nUtil.getMessageKey("label.errors.password.length") + " 6-18";
	            }
	            if (!password.equals(changePasswordVO.getConfirmPassword())) {
	                return I18nUtil.getMessageKey("label.errors.password.not.same");
	            }*/
        // 更新密码
        currentUser.setPassword(EncryptUtil.SHA1(password));
        currentUser.setLastUpdated(new Date());
        currentUser.setLastUpdatedBy(currentUser.getUsername());
        saveOrUpdate(currentUser);

        return "";
    }

    @Override
    public void updateAppsMember(MemberAppsEditVO memberEditVO) {
        User member = get(Long.valueOf(memberEditVO.getId()));
        AddressVO addressVO = null;
        if (StringUtils.isNotBlank(memberEditVO.getAddressExtention()) || StringUtils.isNotBlank(memberEditVO.getDistrict())) {
            addressVO = new AddressVO();
            addressVO.setAddressExtention(memberEditVO.getAddressExtention());
            addressVO.setDistrict(memberEditVO.getDistrict());
        }
        member.setFirstName(memberEditVO.getFirstName());
        member.setLastName(memberEditVO.getLastName());
        member.setFullName(memberEditVO.getFirstName() + " " + memberEditVO.getLastName());
        Shop shop = shopService.get(Long.valueOf(memberEditVO.getShopId()));
        member.setShop(shop);
        member.setGender(memberEditVO.getGender());
        member.setDateOfBirth(memberEditVO.getDateOfBirth());
        member.setLastUpdated(new Date());
        saveOrUpdate(member);
        if (addressVO != null) {
            AddressVO addrvo = addressVO;
            Address address = null;
            if (member.getAddresses() != null && member.getAddresses().size() > 0) {
                address = member.getAddresses().iterator().next();
                address.setLastUpdated(new Date());
                /*    address.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());*/
            } else {
                address = new Address();
                address.setCompany(WebThreadLocal.getCompany());
                address.setUser(member);
                address.setCreated(new Date());
                address.setCreatedBy(WebThreadLocal.getUser().getUsername());
                address.setIsActive(true);
            }
            address.setAddressExtention(addrvo.getAddressExtention());
            address.setCity(addrvo.getCity());
            address.setCountry(addrvo.getCountry());
            address.setDistrict(addrvo.getDistrict());

            addressService.saveOrUpdate(address);
        }
    }

    @Override
    public User saveAppsMember(AppsMemberAddVO appsMemberAddVO) {
        User member = new User();
        member.setEnabled(true);
        member.setAccountType(CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
        if (appsMemberAddVO.getPassword() == null) {
            member.setPassword(EncryptUtil.SHA1(RandomStringUtils.randomAlphanumeric(8))); // 随机生成一个密码

        } else {
            member.setPassword(EncryptUtil.SHA1(appsMemberAddVO.getPassword()));
        }
        /*        member.setPassword(EncryptUtil.SHA1(RandomStringUtils.randomAlphanumeric(8)));    // 随机生成一个密码*/
        member.setFirstName(appsMemberAddVO.getFirstName());
        member.setLastName(StringUtils.isNotBlank(appsMemberAddVO.getLastName()) ? appsMemberAddVO.getLastName() : "N/A");
        member.setFullName(appsMemberAddVO.getFirstName() + " " + appsMemberAddVO.getLastName());
        member.setFullName(appsMemberAddVO.getFullName());
        member.setEmail(appsMemberAddVO.getEmail());
        member.setGender(appsMemberAddVO.getGender());
        member.setDateOfBirth(appsMemberAddVO.getDateOfBirth());
        member.setPreferredContact(appsMemberAddVO.getPreferredContact());
        member.setNotification(appsMemberAddVO.getNotification());

        member.setOptedOut(appsMemberAddVO.getOptedOut() != null ? appsMemberAddVO.getOptedOut() : false);
        member.setHasMPF(false);

        member.getUserGroups().clear();

        Long[] usergroupsIds = appsMemberAddVO.getUsergroupsIds();
        if (usergroupsIds != null && usergroupsIds.length > 0) {
            UserGroup ug = null;
            for (Long ugId : usergroupsIds) {
                ug = userGroupService.get(ugId);
                member.getUserGroups().add(ug);
            }
        }
        member.setActiving(true);
        //member's role is null
        String preferredTherapistId = appsMemberAddVO.getPreferredTherapistId();
        if (StringUtils.isNotBlank(preferredTherapistId)) {
            User therapist = get(Long.valueOf(preferredTherapistId));
            member.setUser(therapist);
        }

        Shop shop = shopService.get(Long.valueOf(appsMemberAddVO.getShopId()));
        member.setShop(shop);
        member.setRemarks(appsMemberAddVO.getRemarks());
        member.setAirmilesMembershipNumber(appsMemberAddVO.getAirmilesMembershipNumber());

        if (appsMemberAddVO.getCommunicationChannelId() != null) {
            CommunicationChannel cc = communicationChannelService.get(appsMemberAddVO.getCommunicationChannelId());
            member.getCommunicationChannels().add(cc);
        }

        member.setCreated(new Date());
        /*  member.setCreatedBy(WebThreadLocal.getUser() !=null ? WebThreadLocal.getUser().getUsername() : "Register");*/
        member.setCreatedBy(appsMemberAddVO.getFirstName() + "Register");
        member.setLastUpdated(new Date());
        member.setLastUpdatedBy(appsMemberAddVO.getFirstName() + "Register");
        /*member.setLastUpdatedBy(WebThreadLocal.getUser() !=null ? WebThreadLocal.getUser().getUsername() : "Register");*/
        member.setUsername(generateNextUsername(CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
        save(member);

        if (StringUtils.isNotBlank(appsMemberAddVO.getMobilePhone())) {
            Phone mPhone = new Phone();
            mPhone.setNumber(appsMemberAddVO.getMobilePhone());
            mPhone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            mPhone.setUser(member);
            phoneService.save(mPhone);
        }
        if (StringUtils.isNotBlank(appsMemberAddVO.getHomePhone())) {
            Phone mPhone = new Phone();
            mPhone.setNumber(appsMemberAddVO.getHomePhone());
            mPhone.setType(CommonConstant.PHONE_TYPE_HOME);
            mPhone.setUser(member);
            phoneService.save(mPhone);
        }
        if (appsMemberAddVO.getAddressVO() != null) {
            AddressVO addrvo = appsMemberAddVO.getAddressVO();
            Address address = new Address();
            address.setAddressExtention(addrvo.getAddressExtention());
            address.setCity(addrvo.getCity());
            address.setCountry(addrvo.getCountry());
            address.setCompany(WebThreadLocal.getCompany());
            address.setDistrict(addrvo.getDistrict());
            address.setUser(member);
            address.setCreated(new Date());
            /*       address.setCreatedBy(WebThreadLocal.getUser() !=null ?WebThreadLocal.getUser().getUsername() : "Register");*/
            member.setCreatedBy(appsMemberAddVO.getFirstName() + "Register");
            address.setIsActive(true);

            addressService.saveOrUpdate(address);
        }
        // saveOrUpdate basic loyalty level for member
        LoyaltyLevel ll = loyaltyLevelService.getActiveLoyaltyLevelByRank(1);
        UserLoyaltyLevelVO userLoyaltyLevelVO = new UserLoyaltyLevelVO();
        userLoyaltyLevelVO.setJoinDate(new Date());
        userLoyaltyLevelVO.setLoyaltyLevel(ll);
        userLoyaltyLevelVO.setUser(member);

        //earn spa points(because of basic level with 0 spa points)
        userLoyaltyLevelService.saveUserLoyaltyLevel(userLoyaltyLevelVO);

 /*      if(register){
        	// 生成注册激活的验证码
            UserCode userCode = userCodeService.saveActivatingCode(member);
            // saveBlock mailshot
            MktMailShot mktMailShot = mktMailShotService.saveRegistrationShot(member);
            // 通过email发送激活的链接
            RegistrationThread.getInstance(mktMailShot, member, userCode).start();
        }
        */
        // 发送设置密码的email
        ForgetPasswordVO forgetPasswordVO = new ForgetPasswordVO();
        forgetPasswordVO.setAccountType(member.getAccountType());
        forgetPasswordVO.setEmail(member.getEmail());
        newMembersSendEmail(forgetPasswordVO);
        return member;
    }


    @Override
    public Object[] clock(StaffInOrOutVo staff) throws ParseException {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        detachedCriteria.add(Restrictions.eq("barcode", staff.getStaffNumber()));
        User user = get(detachedCriteria);
        if (user == null) {
            return new Object[]{0};
        }
        if (!staff.getPin().equals(user.getPin())) {
            return new Object[]{1};
        }

        //查看opening hours
        Shop shop = shopService.get(Long.valueOf(staff.getShop()));
        Date startDate = new Date();
        OpeningHours openingHour = shop.getOpeningHour(startDate);
        String s = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
        Date openTime = DateUtil.stringToDate(s + " " + openingHour.getOpenTime() + ":00", "yyyy-MM-dd HH:mm:ss");
        Date closeTime = DateUtil.stringToDate(s + " " + openingHour.getCloseTime() + ":00", "yyyy-MM-dd HH:mm:ss");

        //查询当天的打卡记录
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date begin = cal.getTime();
        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime();
        DetachedCriteria staffDetached = DetachedCriteria.forClass(StaffInOrOut.class);
        staffDetached.add(Restrictions.between("dateTime", begin, end));
        staffDetached.add(Restrictions.eq("staffId", user.getId()));
        staffDetached.add(Restrictions.eq("shopId", shop.getId()));
        staffDetached.addOrder(Order.desc("id"));
        List<StaffInOrOut> list = staffInOrOutService.list(staffDetached);

        StaffInOrOut staffInOrOut = new StaffInOrOut();
        //开始插入数据库
        Date clockDate = new Date();
        if (list != null && list.size() > 0) {
            if (list.get(0).getType() == 0) {
                Date dateTime = list.get(0).getDateTime();
                Double clockTime = (clockDate.getTime() - dateTime.getTime()) / (1000 * 60 * 60d);
                BigDecimal bg = new BigDecimal(clockTime);
                Double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                staffInOrOut.setDuration(d3 + "");
                staffInOrOut.setType(1l);
                if (startDate.getTime() > closeTime.getTime() && dateTime.getTime() >= closeTime.getTime()) {
                    BigDecimal bg1 = new BigDecimal(((startDate.getTime() - dateTime.getTime()) / (1000 * 60 * 60d)));
                    Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    staffInOrOut.setOt(d4 + "");
                }
                if (startDate.getTime() > closeTime.getTime() && dateTime.getTime() < closeTime.getTime()) {
                    BigDecimal bg1 = new BigDecimal(((startDate.getTime() - closeTime.getTime()) / (1000 * 60 * 60d)));
                    Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    staffInOrOut.setOt(d4 + "");
                }
            } else {
                if (startDate.getTime() < openTime.getTime()) {
                    BigDecimal bg1 = new BigDecimal(((openTime.getTime() - startDate.getTime()) / (1000 * 60 * 60d)));
                    Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    staffInOrOut.setOt(d4 + "");
                }
                staffInOrOut.setType(0l);
            }
        } else {
            if (startDate.getTime() < openTime.getTime()) {
                BigDecimal bg1 = new BigDecimal(((openTime.getTime() - startDate.getTime()) / (1000 * 60 * 60d)));
                Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                staffInOrOut.setOt(d4 + "");
            }
            staffInOrOut.setType(0l);


            // 代表当天第一次打卡，那么需要确认一下前一天是否有结束(clock out)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();
            calendar.add(Calendar.DATE, -1);
            Date yesterday = calendar.getTime();
            DetachedCriteria staffDetachedYesterday = DetachedCriteria.forClass(StaffInOrOut.class);
            staffDetachedYesterday.add(Restrictions.gt("date", DateUtil.dateToString(yesterday, "yyyy-MM-dd")));
            staffDetachedYesterday.add(Restrictions.lt("date", DateUtil.dateToString(today, "yyyy-MM-dd")));
            staffDetachedYesterday.add(Restrictions.eq("staffId", user.getId()));
            staffDetachedYesterday.add(Restrictions.eq("shopId", shop.getId()));
            staffDetachedYesterday.addOrder(Order.desc("id"));
            List<StaffInOrOut> yesterdayAttendanceList = staffInOrOutService.list(staffDetachedYesterday);
            if (yesterdayAttendanceList != null && yesterdayAttendanceList.size() > 0) {
                StaffInOrOut clock = yesterdayAttendanceList.get(0);
                if (clock.getType() != 1) { // 如果昨天的最后一条记录不是clock out
                    staffInOrOut.setWarningMsg(I18nUtil.getMessageKey("label.attendance.clock.out.missing"));
                }
            }
        }
        staffInOrOut.setShopName(shop.getName());
        staffInOrOut.setShopId(Long.valueOf(shop.getId()));
        staffInOrOut.setStaffId(user.getId());
        staffInOrOut.setCreatedBy(WebThreadLocal.getUser().getFullName());
        staffInOrOut.setDate(simpleDateFormat.format(clockDate));
        staffInOrOut.setDateTime(new Date());
        staffInOrOut.setName(user.getUsername());
        // 保存打卡定位到的地址
        Address clockAddress = new Address();
        clockAddress.setLatitude(staff.getLat());
        clockAddress.setLongitude(staff.getLng());
        clockAddress.setCreated(new Date());
        clockAddress.setAddressExtention(staff.getCurrentAddress());
        clockAddress.setCreatedBy(WebThreadLocal.getUser().getFullName());
        clockAddress.setIsActive(true);
        addressService.save(clockAddress);
        staffInOrOut.setAddress(clockAddress);
        staffInOrOutService.save(staffInOrOut);

        return new Object[]{3, staffInOrOut};
    }

    @Override
    public int editClock(Long id, String date, Long type) throws ParseException {

        StaffInOrOut staffInOrOut = staffInOrOutService.get(id);
        if (staffInOrOut == null) {
            return 0;
        }
        Date dateNew = DateUtil.stringToDate(date + ":00", "yyyy-MM-dd HH:mm:ss");
        String basic = DateUtil.dateToString(dateNew, "yyyy-MM-dd");

        Shop shop = shopService.get(Long.valueOf(staffInOrOut.getShopId()));
        OpeningHours openingHour = shop.getOpeningHour(dateNew);
        Date openTime = DateUtil.stringToDate(basic + " " + openingHour.getOpenTime() + ":00", "yyyy-MM-dd HH:mm:ss");
        Date closeTime = DateUtil.stringToDate(basic + " " + openingHour.getCloseTime() + ":00", "yyyy-MM-dd HH:mm:ss");

        //一天开始时间
        Date dayStart = DateUtil.stringToDate(basic + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date DayEnd = DateUtil.stringToDate(basic + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInOrOut.class);
        detachedCriteria.add(Restrictions.between("dateTime", dayStart, DayEnd));
        detachedCriteria.add(Restrictions.eq("staffId", staffInOrOut.getStaffId()));
        detachedCriteria.add(Restrictions.eq("shopId", shop.getId()));
        detachedCriteria.addOrder(Order.desc("id"));
        List<StaffInOrOut> list = staffInOrOutService.list(detachedCriteria);

        if (list != null && list.size() > 0) {
            //判断是否是修改当天的打卡记录
            Integer editClockDate = isEditClockDate(list, id);
            if (editClockDate != null && list.get(editClockDate).getType() == 0) {
                if (type != 0) {
                    return 1;
                }
                if (editClockDate == 0) {
                    if (dateNew.getTime() < openTime.getTime()) {
                        BigDecimal bg1 = new BigDecimal(((openTime.getTime() - dateNew.getTime()) / (1000 * 60 * 60d)));
                        Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        staffInOrOut.setOt(d4 + "");
                    }
                } else {
                    if (list.get(editClockDate - 1).getType() != 1) {
                        return 1;
                    }
                    //修改原来的duration时间
                    StaffInOrOut staffInOrOut1 = list.get(editClockDate - 1);
                    Date dateTime = staffInOrOut1.getDateTime();
                    Double clockTime = (dateTime.getTime() - dateNew.getTime()) / (1000 * 60 * 60d);
                    BigDecimal bg = new BigDecimal(clockTime);
                    Double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    staffInOrOut1.setDuration(d3 + "");
                    staffInOrOutService.update(staffInOrOut1);
                    setOt(dateTime, closeTime, openTime, dateNew, staffInOrOut);
                }
            } else if (editClockDate != null && list.get(editClockDate).getType() == 1) {
                if (type != 1) {
                    return 1;
                }
                if (list.get(editClockDate + 1).getType() != 0) {
                    return 1;
                }
                Date dateTime = list.get(editClockDate + 1).getDateTime();
                Double clockTime = (dateNew.getTime() - dateTime.getTime()) / (1000 * 60 * 60d);
                BigDecimal bg = new BigDecimal(clockTime);
                Double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                staffInOrOut.setDuration(d3 + "");
                setOt(dateNew, closeTime, openTime, dateTime, staffInOrOut);
            } else {
                if (list.get(0).getType() == 0) {
                    if (type != 1) {
                        return 1;
                    }
                    Date dateTime = list.get(0).getDateTime();
                    Double clockTime = (dateNew.getTime() - dateTime.getTime()) / (1000 * 60 * 60d);
                    BigDecimal bg = new BigDecimal(clockTime);
                    Double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    staffInOrOut.setDuration(d3 + "");
                    setOt(dateNew, closeTime, openTime, dateTime, staffInOrOut);
                } else {
                    if (type != 0) {
                        return 1;
                    }
                    if (dateNew.getTime() < openTime.getTime()) {
                        BigDecimal bg1 = new BigDecimal(((openTime.getTime() - dateNew.getTime()) / (1000 * 60 * 60d)));
                        Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        staffInOrOut.setOt(d4 + "");
                    } else {
                        staffInOrOut.setOt("");
                    }
                }
            }
        } else {
            if (type != 0) {
                return 1;
            }
            if (dateNew.getTime() < openTime.getTime()) {
                BigDecimal bg1 = new BigDecimal(((openTime.getTime() - dateNew.getTime()) / (1000 * 60 * 60d)));
                Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                staffInOrOut.setOt(d4 + "");
            }
        }
        staffInOrOut.setDateTime(dateNew);
        String dateToString = DateUtil.dateToString(dateNew, "yyyy-MM-dd HH:mm:ss");
        staffInOrOut.setDate(dateToString);
        staffInOrOut.setType(type);
        staffInOrOutService.update(staffInOrOut);
        return 2;
    }

    private Integer isEditClockDate(List<StaffInOrOut> list, Long id) {
        Integer index = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void setOt(Date date, Date closeTime, Date openingTime, Date dateTime, StaffInOrOut staffInOrOut) {
        if (date.getTime() > closeTime.getTime() && dateTime.getTime() >= closeTime.getTime()) {
            BigDecimal bg1 = new BigDecimal(((date.getTime() - dateTime.getTime()) / (1000 * 60 * 60d)));
            Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            staffInOrOut.setOt(d4 + "");
        } else if (date.getTime() > closeTime.getTime() && dateTime.getTime() < closeTime.getTime()) {
            BigDecimal bg1 = new BigDecimal(((date.getTime() - closeTime.getTime()) / (1000 * 60 * 60d)));
            Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            staffInOrOut.setOt(d4 + "");
        } else {
            if (dateTime.getTime() < openingTime.getTime()) {
                BigDecimal bg1 = new BigDecimal(((openingTime.getTime() - dateTime.getTime()) / (1000 * 60 * 60d)));
                Double d4 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                staffInOrOut.setOt(d4 + "");
            } else {
                staffInOrOut.setOt("");
            }
        }
    }

}
