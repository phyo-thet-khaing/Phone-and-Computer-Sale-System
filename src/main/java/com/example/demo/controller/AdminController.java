package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Admin;
import com.example.demo.model.Category;
import com.example.demo.model.Customer;
import com.example.demo.model.Faq;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.service.AdminService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.FaqService;
import com.example.demo.service.OrderDetailsService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController 
{
	@Autowired
	private AdminService adminService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FaqService faqService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/admin/login/form")
	public String loginForm(Model model)
	{
	    model.addAttribute("admin", new Admin());
	    return "admin/login";
	}
	
	@GetMapping("/admin/login/process") 
	public String loginProcess(@ModelAttribute Admin admin)
	{
		Admin loginAdmin = adminService.findByEmailAndPassword(admin.getEmail(),admin.getPassword());
		
		if(loginAdmin != null)
		{
			session.setAttribute("loginAdmin", loginAdmin);
			return "redirect:/admin/admin-dashboard";
		}
		else
		{
			return "redirect:/admin/login/form";
		}
		
	}
	
	@GetMapping("/admin/admin-dashboard")
	public String adminDashboardPage(Model model)
	{
		
		List<Customer> customers = customerService.findAll();
	      model.addAttribute("customers",customers);
	      
	      int totalUsers = customerService.findAll().size(); // number of customers
	      model.addAttribute("totalUsers", totalUsers);
	      
	      // Orders
	        List<Orders> orders = ordersService.findAll();
	        model.addAttribute("orders", orders);
	        model.addAttribute("totalSales", orders.stream().mapToDouble(Orders::getTotal).sum());

	        // New Orders (example: status = "NEW")
	        int newOrders = (int) orders.stream().filter(o -> o.getStatus().equals("NEW")).count();
	        model.addAttribute("newOrders", newOrders);
		
				
		return "admin/admin-dashboard";
	}
	
	
	
	@GetMapping("/admin/category")
	public String categoryPage(Model model)
	{
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories",categories);
		return "admin/category";
	}
	
	@GetMapping("/admin/category-add")
	public String addCategory(Model model)
	{
		
		model.addAttribute("category",new Category());
		
		return "admin/category-add";
	}
	
	@PostMapping("/admin/category-add/process")
	public String addCategoryProcess(@ModelAttribute Category  category)
	{
		Category addCategory = categoryService.save(category);
		
		if (addCategory != null)
		{
			return "redirect:/admin/category";
		}
		else
			return "redirect:/admin/category-add";
	 
	}
	
	@GetMapping("/admin/category/edit/{id}")
	public String editCategory(@PathVariable Integer id,Model model)
	{

		
		Category category = categoryService.findById(id);
		
		model.addAttribute("category",category);
		return "admin/category-update";
		
		 
	}
	
	@PostMapping("/admin/category-edit/process")
	public String editCategoryProcess(@ModelAttribute Category category)
	{
		Category addCategory = categoryService.update(category);
		if (addCategory != null)
		{
			return "redirect:/admin/category";
		}
		else
			return "redirect:/admin/category-update";
		
	}
	
	@GetMapping("/admin/category/delete/{id}")
	public String deleteCategory(@PathVariable Integer id)
	{
		boolean isDeleted = categoryService.deleteById(id);
		return "redirect:/admin/category";
	}
	
//	
//	@GetMapping("/admin/product")
//	public String product(Model model)
//	{
//		List<Product> products = productService.findAll();
//		model.addAttribute("products",products);
//		return"admin/product";
//	}
	
	@GetMapping("/admin/product")
    public String product(@RequestParam(defaultValue = "1") int page, 
    		  @RequestParam(defaultValue = "productName") String sort,
    		  @RequestParam(defaultValue = "asc") String dir,            // sort direction
              @RequestParam(required = false) String search,            // search keyword// sort field
    		Model model) 
    {
		int pageSize = 7; // number of products per page
		  
        // Create Sort object
        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortObj = Sort.by(direction, sort);
  
        // PageRequest uses 0-based index
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sortObj);
  
        Page<Product> productPage;
  
        if (search != null && !search.isEmpty()) 
        {
            // Assuming you have a service method that handles search with pagination
            productPage = productService.searchPaginated(search, pageRequest);
        } 
        else 
        {
            productPage = productService.findPaginated(pageRequest);
        }
  
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");
        model.addAttribute("search", search);
  
        return "admin/product";
    }
	
	@PostMapping("/admin/product/import-excel")
	public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
	    if (file.isEmpty()) 
	    {
	        redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
	        return "redirect:/admin/product";
	    }

	    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) 
	    {
	        Sheet sheet = workbook.getSheetAt(0);

	        for (int i = 1; i <= sheet.getLastRowNum(); i++)
	        {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            Product product = new Product();

	            // Name
	            Cell nameCell = row.getCell(1);
	            if (nameCell != null) 
	            {
	                product.setProductName(nameCell.getStringCellValue());
	            }

	            // Category (by name)
	            Cell categoryCell = row.getCell(2);
	            if (categoryCell != null) 
	            {
	                String categoryName = categoryCell.getStringCellValue();
	                if (categoryName != null && !categoryName.isEmpty())
	                
	                {
	                    Category category = categoryService.findByName(categoryName);
	                    if (category != null)
	                    {
	                        product.setCategory(category);
	                    }
	                }
	            }

	            // Quantity (numeric or string)
	            Cell quantityCell = row.getCell(3);
	            if (quantityCell != null) {
	                if (quantityCell.getCellType() == CellType.NUMERIC) {
	                    product.setQuantity(String.valueOf((int) quantityCell.getNumericCellValue()));
	                } else if (quantityCell.getCellType() == CellType.STRING) {
	                    product.setQuantity(quantityCell.getStringCellValue());
	                } else {
	                    product.setQuantity("0");
	                }
	            }


	            // Price (numeric only)
	            Cell priceCell = row.getCell(4);
	            if (priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
	                product.setPrice(priceCell.getNumericCellValue());
	            }

	            // Save product
	            productService.save(product);
	        }

	        redirectAttributes.addFlashAttribute("success", "Products imported successfully!");
	    } catch (Exception e) 
	    {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Error importing Excel: " + e.getMessage());
	    }

	    return "redirect:/admin/product";
	}

	
	

	@GetMapping("/admin/product-add")
	public String showAddProductForm(Model model) 
	
	{
		List<Category> categories = categoryService.findAll();
	    model.addAttribute("product", new Product());
	    model.addAttribute("categories",categories);
	    return "admin/product-add"; // adjust view name as needed
	}
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/Upload";
	@PostMapping("/admin/product-add/process")
	public String addProductProcess(@ModelAttribute Product product, @RequestParam MultipartFile file) throws IOException 
	{
		String getFileName = file.getOriginalFilename();
		
		Path filePath = Paths.get(uploadDirectory, getFileName);
		Files.write(filePath, file.getBytes());
		
		product.setImage(getFileName);
		product.setDate(new Date());
		
		Product	addProduct = productService.save(product);
		
		if (addProduct != null)
		{
			return "redirect:/admin/product";
		}
		else
			return "redirect:/admin/product-add";
		
	}
	
	@GetMapping("/admin/product/edit/{id}")
	public String editProduct(@PathVariable Integer id,Model model)
	{
		
		List<Category> categories = categoryService.findAll();
		
		model.addAttribute("categories",categories);
		
		Product product = productService.findById(id);
		
		model.addAttribute("product",product);
		return "admin/product-edit";
	}
	
	@PostMapping("/admin/product-edit/process")
	public String editProductProcess(@ModelAttribute Product product, @RequestParam MultipartFile file) throws IOException 
	{
		String getFileName = file.getOriginalFilename();
		
		Path filePath = Paths.get(uploadDirectory, getFileName);
		Files.write(filePath, file.getBytes());
		
		product.setImage(getFileName);
		
		
		Product	addProduct = productService.update(product);
		
		if (addProduct != null)
		{
			return "redirect:/admin/product";
		}
		else
			return "redirect:/admin/product-edit";
		
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable Integer id)
	{
		boolean isDeleted =productService.deleteById(id);
		
		return "redirect:/admin/product";
	}
	
	@GetMapping("/admin/customer")
    public String customerForm(Model model)
    {  
      
      List<Customer> customers = customerService.findAll();
      model.addAttribute("customers",customers);

      return "admin/customer";
    }
	
	@GetMapping("/admin/customer-add")
	public String customerAdd(Model model) {
	    model.addAttribute("customer", new Customer());
	    return "admin/customer-add";
	}
	
	 @GetMapping("/admin/customer/delete/{id}")
	    public String customerDeleteProcess(@PathVariable Integer id)
	    {
	      
	      
	      boolean isDeleted = customerService.deleteById(id);
	      
	      return "redirect:/admin/customer";
	    }

	 @GetMapping("/admin/faq")
	 public String faqPage(Model model) {
	     List<Faq> faqList = faqService.findAll();
	     model.addAttribute("faqs", faqList);
	     return "admin/faq";   
	 }
	 
	 @GetMapping("/contact")
	 public String contactPage(Model model) {
	     model.addAttribute("pageTitle", "Contact Us");
	     return "customer/contact";
	 }

	 
	 @GetMapping("/admin/faq-add")
	 public String addFaq(Model model) {

	     model.addAttribute("faq", new Faq());

	     return "admin/faq-add";
	 }

	 @PostMapping("/admin/faq-add/process")
	 public String addFaqProcess(@ModelAttribute Faq faq) {

	     Faq addedFaq = faqService.save(faq);

	     if (addedFaq != null) {
	         return "redirect:/admin/faq";
	     } else {
	         return "redirect:/admin/faq-add";
	     }
	 }
	 
	// Show Edit Form
	 @GetMapping("/admin/faq/edit/{id}")
	 public String editFaq(@PathVariable Integer id, Model model) {

	     Faq faq = faqService.findById(id);  // fetch existing FAQ

	     model.addAttribute("faq", faq);
	     return "admin/faq-edit";  // Thymeleaf edit form
	 }

	 // Process Edit Form
	 @PostMapping("/admin/faq-edit/process")
	 public String editFaqProcess(@ModelAttribute Faq faq) {

	     Faq updatedFaq = faqService.updateFaq(faq);

	     if (updatedFaq != null) {
	         return "redirect:/admin/faq";
	     } else {
	         return "redirect:/admin/faq-edit";
	     }
	 }
	 
	 @GetMapping("/admin/faq/delete/{id}")
	 public String deleteFaq(@PathVariable Integer id) {
	     boolean isDeleted = faqService.deleteById(id);
	     return "redirect:/admin/faq";
	 }

	 @GetMapping("/admin/order")
	 public String Order(@RequestParam(defaultValue="1") int page,Model model)
	 {
		 int pageSize=8;
		 Page<Orders> ordersPage = ordersService.findPaginated(page,pageSize);
		
		 
		 //add list to model
		 
		 model.addAttribute("currentPage",page);
		 model.addAttribute("totalPages",ordersPage.getTotalPages());
		 model.addAttribute("totalItems", ordersPage.getTotalElements());
	     model.addAttribute("orders", ordersPage.getContent());
		 
		 return "admin/order";
	 }
	 
		@GetMapping("/admin/order-detail/{id}")
		public String orderDetail(@PathVariable("id")Integer id,Model model)
		{
			System.out.println("order detail");
			Orders order =ordersService.findById(id);
			System.out.println(" ****  " + order.getOrderDetails() +  " **** ");
			System.out.println();
			
			model.addAttribute("order",order);

			
			return "admin/order-detail";
			
			
		}
		
		


	 
}
