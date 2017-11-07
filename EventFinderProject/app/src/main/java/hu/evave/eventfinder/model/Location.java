package hu.evave.eventfinder.model;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.math.BigDecimal;

import hu.evave.eventfinder.model.converter.CoordinateConverter;


@Entity(nameInDb = "location")
public class Location {

    @Id
    @Property(nameInDb = "id")
    private String id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "country")
    private String country;

    @Property(nameInDb = "country_code")
    private String countryCode = "HU";

    @Property(nameInDb = "city")
    private String city;

    @Property(nameInDb = "zip_code")
    private String zipCode;

    @Property(nameInDb = "address")
    private String address;

    @Property(nameInDb = "lat")
    @Convert(columnType = Long.class, converter = CoordinateConverter.class)
    @NotNull
    private BigDecimal lat;

    @Property(nameInDb = "lon")
    @Convert(columnType = Long.class, converter = CoordinateConverter.class)
    @NotNull
    private BigDecimal lon;


    @Generated(hash = 1318860975)
    public Location(String id, String name, String country, String countryCode,
                    String city, String zipCode, String address, @NotNull BigDecimal lat,
                    @NotNull BigDecimal lon) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.countryCode = countryCode;
        this.city = city;
        this.zipCode = zipCode;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    @Generated(hash = 375979639)
    public Location() {
    }


    @Override
    @Keep
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return id != null ? id.equals(location.id) : location.id == null;

    }

    @Override
    @Keep
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    @Keep
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (name != null) {
            result.append(name);
            result.append('\n');
        }
        if (zipCode != null) {
            result.append(zipCode);
            result.append(' ');
        }
        result.append(city);
        result.append(",\n");
        result.append(address);

        return result.toString();
    }

    @Keep
    public android.location.Location toAndroidLocation() {
        android.location.Location result = new android.location.Location("");

        if(lat == null)
            lat = new BigDecimal("47.501115");
        if(lon == null)
            lon = new BigDecimal("19.053196");

        result.setLatitude(lat.doubleValue());
        result.setLongitude(lon.doubleValue());

        return result;
    }

    @Keep
    public LatLng toLatLng() {
        return new LatLng(lat.doubleValue(), lon.doubleValue());
    }

    public BigDecimal getLon() {
        return this.lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return this.lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @Keep
    public String getAddress() {
        if (address == null) {
            return "";
        }

        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
