package mis.models;


public class Order
{
    private String order_num;
    private String order_date;
    private int total_price;
    private String customer_name;
    private String customer_address;
    private String customer_phtone;
    
    public Order() {
    }

    public Order(String order_num, String order_date, int total_price, String customer_name) {
        this.order_num = order_num;
        this.order_date = order_date;
        this.total_price = total_price;
        this.customer_name = customer_name;
    }
    
    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phtone() {
        return customer_phtone;
    }

    public void setCustomer_phtone(String customer_phtone) {
        this.customer_phtone = customer_phtone;
    }
}
