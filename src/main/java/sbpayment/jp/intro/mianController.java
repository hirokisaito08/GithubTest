package sbpayment.jp.intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class mianController {
	
	@GetMapping("/intro")
	public String index() {
		return "index";
	}
	
	@Autowired
    private JdbcTemplate jdbc;

	
	



//	@PostMapping("/classification")
//	public String Post(int id,String reason, int acc, RedirectAttributes attr){
//		attr.addAttribute("id", id);
//		attr.addAttribute("reason", reason);
//		attr.addAttribute("acc", acc);
//			jdbc.update("INSERT INTO accumulation (id,reason,acc) VALUES (?,?,?)",id,reason,acc);
//	attr.addFlashAttribute("data",jdbc.queryForList("SELECT * FROM accumulation"));
//	
//	   return "redirect:/classification";
//	}
	
	@PostMapping("/classification")
	public String Post(String reason[], String acc[], RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
//		attr.addAttribute("sreaon", reason);
//		attr.addAttribute("acc", acc);
		System.out.println(reason[i]);
		if(acc[i].equals(null) != true && acc[i].isEmpty() != true) {
			
			jdbc.update("INSERT INTO accumulation (reason,acc) VALUES (?,?)",reason[i],Integer.valueOf(acc[i]));
//			attr.addFlashAttribute("data",jdbc.queryForMap("SELECT * FROM accumulation"));
		}
		}
	return "redirect:/classification";
	}
	
	
	////
//	@GetMapping("/classification")
//	public String list(Model model) {
//		
//		model.addAttribute("data",jdbc.queryForList("SELECT * FROM accumulation"));
//		return "/classification";
//	}
	/////
	
	
	
	@GetMapping("/Registration")
	public String Registration() {
		return "Registration";
	}
	//6/15
	@PostMapping("/Registration")
    public String Post(int id, String reason, int acc,RedirectAttributes attr){
		attr.addFlashAttribute("accumulation",jdbc.queryForList("SELECT * FROM accumulation"));
        return "redirect:/Registration";
	}
	
	
	@GetMapping("/classification")
	public String classification() {
	//	attr.addFlashAttribute("data",jdbc.queryForMap("SELECT * FROM accumulation"));//now
		return "classification";
	}
	
}

