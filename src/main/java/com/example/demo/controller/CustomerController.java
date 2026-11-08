package com.example.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Cart;
import com.example.demo.model.Category;
import com.example.demo.model.Customer;
import com.example.demo.model.Faq;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.FaqService;
import com.example.demo.service.OrderDetailsService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/customer")
public class CustomerController
{
	//Create list
	List<Cart> carts = new ArrayList<Cart>();
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FaqService faqService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productService;
	

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;

	@GetMapping("/home")
	public String customerHome(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "productName") String sort,
	        @RequestParam(defaultValue = "asc") String dir,
	        @RequestParam(required = false) String search,
	        Model model) {

	    int pageSize = 7; // products per page

	    // Sort object
	    Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	    Sort sortObj = Sort.by(direction, sort);

	    // PageRequest (0-based index)
	    PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sortObj);

	    Page<Product> productPage;

	    if (search != null && !search.isEmpty()) {
	        // Use service method that handles search with pagination
	        productPage = productService.searchPaginated(search, pageRequest);
	    } else {
	        productPage = productService.findPaginated(pageRequest);
	    }

	    // Add attributes to model
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("totalItems", productPage.getTotalElements());
	    model.addAttribute("products", productPage.getContent());
	    model.addAttribute("sortField", sort);
	    model.addAttribute("sortDir", dir);
	    model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");
	    model.addAttribute("search", search);

	    // Add categories
	    List<Category> categories = categoryService.findAll();
	    model.addAttribute("categories", categories);

	    // Optional message
	    model.addAttribute("message", "Welcome to Customer Home!");

	    return "customer/home";
	}


	
	 @GetMapping("/about")
	 public String aboutPage(Model model) 
	 {		
		    // Fetch categories
		    List<Category> categories = categoryService.findAll();
		    model.addAttribute("categories", categories); 
		 
			 List<Faq> faqList = faqService.findAll();
		     model.addAttribute("faqs", faqList);
	        model.addAttribute("pageTitle", "About Us");
	        return "customer/about";
	  }
	 
	 @GetMapping("/contact")
	 public String contactPage(Model model) 
	 {
		 
		 	// Fetch categories
		    List<Category> categories = categoryService.findAll();
		    model.addAttribute("categories", categories);
		 
	     model.addAttribute("pageTitle", "Contact Us");
	     return "customer/contact";
	 }

    
    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable Integer id, Model model)
    {

        Product product = productService.findById(id);
        model.addAttribute("product", product);
	    List<Category> categories = categoryService.findAll();
	    model.addAttribute("categories", categories);

        return "customer/product-detail"; 
    }
    
	@GetMapping("/cart")
	public String cartProduct(Model model)
	{
	      
		Double total = 0.0;
	//retrieve list from session
		if (session.getAttribute("carts") != null)
		{
			carts = (List<Cart>) session.getAttribute("carts");
		}

	
		//loop list
		if (carts != null)
		{
			for (Cart cart:carts)
			{
				//total += quantity*price;
				total = total + cart.getPrice() * cart.getQuantity();
			}		
			
		}
	
		//to add model
		model.addAttribute("total",total);
		
		return "customer/cart";
	}
	
	@GetMapping("/cart/add/{id}")
	public String addCart(@PathVariable Integer id)
	{
		//create list 
		List<Cart> carts = new ArrayList<Cart>();
		//retrieve product from db with id 
		Product product = productService.findById(id);
		
		//Boolean
		Boolean flag = false;
		
		//new session
		if(session.getAttribute("carts") == null)
		{
			System.out.println("New session");
			//create cart
			Cart cart = new Cart();
			
			//insert cart from product
			cart.setId(product.getId());
			cart.setName(product.getProductName());
			cart.setDescription(product.getDescription());
			cart.setPrice(product.getPrice());
			cart.setImage(product.getImage());
			cart.setCategoryName(product.getCategory().getName());
			cart.setQuantity(1);
			
			
			
		
			
			//insert cart to list
			carts.add(cart);
			
			//insert list to session
			session.setAttribute("carts", carts);
		}
		else //old section
		{
			//Retrieve list from session
			carts = (List<Cart>) session.getAttribute("carts");
			
			//old cart
			for(Cart cart : carts)
			{
				if(cart.getId() == id)
				{
					//update quantity
					cart.setQuantity(cart.getQuantity()+1);
					
					//flag
					flag = true;
					
					//Break
					break;
				}
					
			}
			System.out.println("old session");
			//old session
			//create cart
			
			if(!flag)
			{
				Cart cart = new Cart();
				//insert into Cart from product
				cart.setId(product.getId());
				cart.setName(product.getProductName());
				cart.setDescription(product.getDescription());
				cart.setPrice(product.getPrice());
				cart.setImage(product.getImage());
				cart.setCategoryName(product.getCategory().getName());
				cart.setQuantity(1);
				cart.setCategoryName(product.getCategory().getName());
				
				//insert into list --Carts
				carts.add(cart);
				
				//insert into Session(List)
				session.setAttribute("carts", carts);
				
			}
		}
		
		return "redirect:/customer/home";
		}
	
	@GetMapping("/cart-update/{cartId}")
    public String updateCart(@PathVariable("cartId") Integer cartId, @RequestParam("action") String action) 
	{
		if(session.getAttribute("carts") != null)
		{
			carts = (List<Cart>) session.getAttribute("carts");
		}
        // Perform the update based on the cartId and action (subtract, add, etc.)
		
		 if (carts != null) {
		        for (Iterator<Cart> iterator = carts.iterator(); iterator.hasNext(); ) {
		            Cart cart = iterator.next();
		            if (cart.getId() == cartId) {
		                if ("subtract".equals(action)) 
		                {
		                    // If subtracting, decrease quantity
		                    if (cart.getQuantity() > 1) 
		                    {
		                        cart.setQuantity(cart.getQuantity() - 1);
		                    } else 
		                    {
		                        // If the quantity becomes 0, remove the product from the cart
		                        iterator.remove();
		                    }
		                } 
		                
		                else if ("add".equals(action)) 
		                {
		                    // If adding, increase quantity
		                    cart.setQuantity(cart.getQuantity() + 1);
		                }
		                break;
		            }
		        }

		        // After the cart is updated, save it back to the session
		        session.setAttribute("carts", carts);
		    }

		    // Redirect back to the cart page to display updated cart
		    return "redirect:/customer/cart";
		}
	

	@GetMapping("/cart/delete/{id}")
	public String deleteCart(@PathVariable Integer id)
	{
		//retrieve list from session
		List<Cart> carts = (List<Cart>)session.getAttribute("carts");
		
		//create iterator 
		Iterator<Cart> it = carts.iterator();
		
		
		//to delete
		while(it.hasNext())
		{
			if(it.next().getId() == id)
			{
				it.remove();
				break;
			}
		}
		
		//show in session
		session.setAttribute("carts", carts);
		
		return "redirect:/customer/cart";
	}
	@GetMapping("/login")
	public String LoginForm(Model model)
	{
		model.addAttribute("customer",new Customer());
		return "customer/login";
	
	}
	
	@PostMapping("/login/process")
	public String LoginSuccess(@ModelAttribute Customer customer,RedirectAttributes redirectAttrs)
	{
		System.out.println(customer.getEmail() + customer.getPassword());
		

		Customer loginCustomer = customerService.findByEmail(customer.getEmail());
	
		
		if (loginCustomer != null && passwordEncoder.matches(customer.getPassword(),loginCustomer.getPassword()))
		{
			session.setAttribute("loginCustomer", loginCustomer);
			return "redirect:/customer/home";
		}
		else
		{
			redirectAttrs.addFlashAttribute("error", "Invalid email or password!");
			return "redirect:/customer/login";
		}
			
			
	}
	
	@GetMapping("/register")
	public String Register(Model model)
	{
		model.addAttribute("customer",new Customer());	
		return "customer/register";	
	}
	
	
	@PostMapping("/register/process")
	public String RegisterSuccess(@ModelAttribute Customer customer)
	{
		System.out.println(customer.getName()+customer.getEmail()+customer.getAddress()+customer.getPhone());
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		
		customer.setLastAccessData(new Date(System.currentTimeMillis()));
		
		Customer registerCustomer = customerService.save(customer);
		
		if(registerCustomer != null)
			return "redirect:/customer/register";
		else
			return "redirect:/customer/login";
		
	}
	
	@GetMapping("/invoice")
	public String invoice(Model model)
	{
		Double total = 0.0;
		Customer customer =(Customer) session.getAttribute("loginCustomer");
		//login user 
		if(session.getAttribute("loginCustomer")!= null)
		{
			//1.retrieve list from session
			carts = (List<Cart>) session.getAttribute("carts");
			
			//2.loop --> total
			if(carts != null)
			{
				for(Cart cart : carts)
				{
					total += (cart.getPrice() * cart.getQuantity());
				}
			}
			
			//3.create order object
			Orders order = new Orders();
			//4.set data to order
			order.setOrder_date(new Date(System.currentTimeMillis()));
			order.setTotal(total);
			order.setAddress(customer.getAddress());
			order.setStatus("Processing");
			order.setCustomer(customer);
			
			//5. save order to database
			Orders saveOrder = orderService.save(order);
			
			
			//6.loop--> order detail save with recent order id
			List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
			if (carts != null)
			{
				for (Cart cart :carts)
				{
					//create Order detail object
					OrderDetails orderDetail = new OrderDetails();
					
					//set data to OrderDetail
					Product product = new Product();
					product.setId(cart.getId());
					product.setProductName(cart.getName());
					product.setPrice(cart.getPrice());
					product.setImage(cart.getImage());
					
					
					orderDetail.setQuantity(cart.getQuantity());
					orderDetail.setSetPrice(cart.getPrice() * cart.getQuantity());
					orderDetail.setOrder(saveOrder);
					orderDetail.setProduct(product);
					
					//save to data base
					orderDetailsService.save(orderDetail);
					
					//add to list
					orderDetails.add(orderDetail);
				}
			}
			
			saveOrder.setOrderDetails(orderDetails);

			model.addAttribute("saveOrder", orderService.findById(saveOrder.getId()));
			
			
			
			return "customer/invoice";
		}
		
		else //not login user
		{
			return "redirect:/customer/login";
		}
		
	}

}

