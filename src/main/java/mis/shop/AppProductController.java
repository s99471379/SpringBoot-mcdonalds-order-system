package mis.shop;

import java.util.List;
import mis.models.Product;
import mis.models.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppProductController {

    private final ProductDAO productDao = new ProductDAO();

    //url根路徑 或稱為 家路徑 也適合
    @RequestMapping("/product_crud")
    public String home(Model model) {

        List<Product> products = productDao.getAllProducts();

        System.out.println(products.get(0).toString());

        model.addAttribute("listProducts", products);
        System.out.println(model.toString());
        return "product_crud/home.html";
    }

    //新增產品
    //@RequestMapping("/showNewProductForm")
    //@PostMapping("/showNewProductForm")
    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        // 新增一個產品物件，內容填入範例資料，全都不填入資料也是可以
        Product product = new Product();

        product.setProduct_id("p-j-XXX");
        product.setCategory("單點");
        product.setName("大麥克");
        product.setPrice(75);
        //product.setPhoto("bigmac.jpg");
        product.setDescription("全球狂熱50年，口感樂趣無窮。");

        model.addAttribute("product", product);

        return "product_crud/new_product.html"; //渲染並開啟一個新增產品網頁
    }

    //確定新增一筆資料，進行寫入資料庫，並重新導向到網站的根路徑
    @PostMapping("/createProduct")
    //public String saveProduct(@ModelAttribute("product") Product product) {
    public String saveProduct(Product product) {
        productDao.insert(product);
        return "redirect:/product_crud";
    }

    // 修改產品Modify Update
     // 寫法1 使用GET傳遞產品編號 後端使用@RequestPath抓取路徑參數的方式
    // http://localhost:8080/showFormForUpdate/p-j-000
    //@RequestMapping("/showFormForUpdate/{pid}")
    @GetMapping("/showFormForUpdate/{pid}")
    public String showFormForUpdate_v0(@PathVariable(value = "pid") String product_id, Model model) {
        // get product
        Product product = productDao.findById(product_id);
        System.out.println(product.getProduct_id());

        // product bean
        model.addAttribute("product", product);
        return "product_crud/update-product.html";
    }

    //前端傳送資料()=>到後端，使用String product_id
    // 修改產品Modify Update後端傳送資料(一項新產品初值)=>到前端，使用Model model  
    // 寫法1 使用GET or POST傳遞產品編號 後端使用@RequestParam抓取參數的方式
    // http://localhost:8080/showFormForUpdate?pid=p-j-000
    @GetMapping("/showFormForUpdate")
    //@RequestMapping("/showFormForUpdate")
    public String showFormForUpdate_v1(@RequestParam(name="pid") String product_id, Model model) {
        // get product
        Product product = productDao.findById(product_id);
        System.out.println(product.getProduct_id());

        // product bean
        model.addAttribute("product", product);
        return "product_crud/update-product.html";
    }


    
    //確定更新這一筆資料，進行寫入資料庫，並重新導向到網站的根路徑
    @PostMapping("/updateProduct")
    //@RequestMapping("/updateProduct")
    public String updateProduct(Product product) {
    //public String updateProduct(@ModelAttribute("product") Product product) {
        System.out.println(product.getProduct_id());        
        this.productDao.update(product);
        return "redirect:/product_crud/";
    }
    
    
    
    //產品刪除功能
    /*
    //寫法A  http://localhost:8080/deleteProduct/pid=p-j-000  可以刪除成功
    @GetMapping("/deleteProduct/{pid}")
    public String deleteProduct(@PathVariable(value = "pid") String product_id) {
        this.productDao.delete(product_id);
        return "redirect:/";
    }
    */

    //寫法D 較安全
    //使用Post的方式較佳。
    //@RequestParam參數: name= "product_id" 或 value= "product_id" 皆可
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(name = "product_id") String product_id) {
        this.productDao.delete(product_id);
        return "redirect:/product_crud/";
    }



}
