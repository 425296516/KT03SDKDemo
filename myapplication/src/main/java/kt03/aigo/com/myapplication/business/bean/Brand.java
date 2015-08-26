package kt03.aigo.com.myapplication.business.bean;


/**
 * 电器品牌,对应表tb_brand
 */
public class Brand implements Cloneable {

    private int id;//id

    private String brand;//品牌名

    private String brand_tra;//品牌繁体名

    private String sortLetters;//品牌全拼

    private String py;//品牌首字母拼音

    private String remarks; //可以用于筛选指定电器类型的品牌集合

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSortLetters() {

        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand_tra() {
        return brand_tra;
    }

    public void setBrand_tra(String brand_tra) {
        this.brand_tra = brand_tra;
    }

    /**
     * @return the brand copy
     */
    @Override
    public Brand clone() {
        Brand brand = new Brand();
        brand.setId(id);

        return brand;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", brand_tra='" + brand_tra + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                ", py='" + py + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
