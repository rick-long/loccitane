package org.spa.vo.app.callback;

import com.spa.constant.CommonConstant;
import org.spa.model.shop.Address;
import org.spa.model.shop.Phone;
import org.spa.model.shop.Shop;

import java.io.Serializable;
import java.util.Set;

public class ShopCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private Long id;
    private String reference;
    private String name;
    private String longitude;
    private String latitude;
    private String addressExtention;
    private String phone;
    private String prefix;
    public ShopCallBackVO() {
        super();
    }
    public ShopCallBackVO(Shop shop){
        this.name=shop.getName();
        this.id=shop.getId();
        this.reference=shop.getReference();
        this.longitude=this.getAddresse(shop.getAddresses()).getLongitude();
        this.latitude=this.getAddresse(shop.getAddresses()).getLatitude();
        this.addressExtention=this.getAddresse(shop.getAddresses()).getAddressExtention();
        this.phone=byTypeGetMobilePhone(shop.getPhones());
        this.prefix=shop.getPrefix();
    }
    public Address getAddresse(Set<Address> addresses){
        Address address =new Address();
        if(addresses!=null&&addresses.size()>0 ){
            for(Address address2:addresses){
                address=address2;
                continue;
            }
        }

        return address;
    }
    private String byTypeGetMobilePhone(Set<Phone> phones){
        String mobilePhone="";
        if(phones!=null&&phones.size()>0){
            for(Phone phone: phones){
                if(phone.getType().equals(CommonConstant.PHONE_TYPE_BUSINESS)){
                    mobilePhone=phone.getNumber();
                }
            }
        }
        return mobilePhone;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddressExtention() {
        return addressExtention;
    }

    public void setAddressExtention(String addressExtention) {
        this.addressExtention = addressExtention;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
