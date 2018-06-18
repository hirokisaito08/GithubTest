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

	
///////////////////////////////////////////////収入
	
	@PostMapping("/classification")
	public String Post(String reason[], String sal[], RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("sal", sal);
		System.out.println(reason[i]);
			if(sal[i].equals(null) != true && sal[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO income (reason,sal) VALUES (?,?)",reason[i],Integer.valueOf(sal[i]));
				attr.addFlashAttribute("data",jdbc.queryForList("SELECT * FROM income"));
			}
		}
	return "redirect:/classification";
	}
	@GetMapping("/classification")
	public String list2(Model model) {
		model.addAttribute("data",jdbc.queryForList("SELECT * FROM income"));
		return "/classification";
	}

	/////////////////////////////////////////////支出
	@PostMapping("/outClassification")
	public String Post1(String reason[], String out[], RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("out", out);
		System.out.println(reason[i]);
			if(out[i].equals(null) != true && out[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO spending (reason,out) VALUES (?,?)",reason[i],Integer.valueOf(out[i]));
				attr.addFlashAttribute("data1",jdbc.queryForList("SELECT * FROM spending"));
			}
		}
	return "redirect:/outClassification";
	}
	@GetMapping("/outClassification")
	public String list3(Model model) {
		model.addAttribute("data1",jdbc.queryForList("SELECT * FROM spending"));
		return "/outClassification";
	}	
/////////////////////////////////////////////////貯金
	@PostMapping("/accClassification")
	public String Post2(String reason[], String acc[], RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("acc", acc);
		System.out.println(reason[i]);
			if(acc[i].equals(null) != true && acc[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO accumulation (reason,acc) VALUES (?,?)",reason[i],Integer.valueOf(acc[i]));
				attr.addFlashAttribute("data2",jdbc.queryForList("SELECT * FROM accumulation"));
			}
		}
	return "redirect:/accClassification";
	}
	@GetMapping("/accClassification")
	public String list4(Model model) {
		model.addAttribute("data2",jdbc.queryForList("SELECT * FROM accumulation"));
		return "/accClassification";
	}		
	
	
////////////////////////////////////////////////////
	@GetMapping("/Registration")
	public String list(Model model) {
		return "/Registration";
	}
	//6/15
	@PostMapping("/Registration")
    public String Post(int id, String reason, int acc,RedirectAttributes attr){
        return "redirect:/Registration";
	}
}

