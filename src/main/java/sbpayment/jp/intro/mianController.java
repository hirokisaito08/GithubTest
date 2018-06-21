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
	public String index2() {
		return "index2";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@Autowired
    private JdbcTemplate jdbc;

////////////////////////////////////////////////////
@GetMapping("/Registration")
public String list(Model model) {
model.addAttribute("data_sum",jdbc.queryForList("SELECT sum(sal) FROM income").get(0).get("sum(sal)"));
model.addAttribute("data_sum1",jdbc.queryForList("SELECT sum(out) FROM spending").get(0).get("sum(out)"));
model.addAttribute("data_sum2",jdbc.queryForList("SELECT sum(acc) FROM accumulation").get(0).get("sum(acc)"));
model.addAttribute("data_day",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("day"));
model.addAttribute("data_month",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("month"));
model.addAttribute("data_year",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)" ).get(0).get("year"));
//model.addAttribute("data_day",jdbc.queryForList("SELECT * FROM someday" ).get(0).get("day"));
//model.addAttribute("data_month",jdbc.queryForList("SELECT * FROM someday").get(0).get("month"));
//model.addAttribute("data_year",jdbc.queryForList("SELECT * FROM someday").get(0).get("year"));
return "/Registration";
}
//6/15
@PostMapping("/Registration")
public String Post(String day,RedirectAttributes attr){
	System.out.println(day);
	String[] dayA=day.split("/");
	String a0=dayA[0];
	String a1=dayA[1];
	String a2=dayA[2];
	int dayb1=Integer.parseInt(a1);
	int dayb2=Integer.parseInt(a2);
	if(dayb1/10==0) {
		dayA[1]=0+dayA[1];
	}else {dayA[1]=dayA[1];}
	if(dayb2/10==0) {
		dayA[2]=0+dayA[2];
	}else {dayA[2]=dayA[2];}
	for(int i=0;i<dayA.length;i++) {
		System.out.println(dayA[i]);
	}
	//if(jdbc.queryForList("SELECT * FROM someday WHERE year = ? and month = ? and day = ?",dayA[0],dayA[1],dayA[2]).size()==0) {
		jdbc.update("INSERT INTO someday (year,month,day) VALUES (?,?,?)",dayA[0],dayA[1],dayA[2]);
//	}

	return "redirect:/Registration";

}



//////////////////////////////////////////////////////mainR
@GetMapping("/mainRegistration")
public String list5(Model model) {
	model.addAttribute("data_sum",jdbc.queryForList("SELECT sum(sal) FROM income").get(0).get("sum(sal)"));
	model.addAttribute("data_sum1",jdbc.queryForList("SELECT sum(out) FROM spending").get(0).get("sum(out)"));
	model.addAttribute("data_sum2",jdbc.queryForList("SELECT sum(acc) FROM accumulation").get(0).get("sum(acc)"));

	return "/mainRegistration";
}
//6/15
//@PostMapping("/mainRegistration")
//public String Post3(int id, String reason, int acc,RedirectAttributes attr){
//
//
//
//return "redirect:/mainRegistration";
//}
//


	
///////////////////////////////////////////////収入
	
	@PostMapping("/classification")
	public String Post(String reason[], String sal[],int year,int month,int day, RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("sal", sal);
		System.out.println(reason[i]);
		System.out.println(year+"/"+month+"/"+day);
			if(sal[i].equals(null) != true && sal[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO income (reason,sal,year,month,day) VALUES (?,?,?,?,?)",reason[i],Integer.valueOf(sal[i]),year,month,day);
				System.out.println(year/month/day);
				attr.addFlashAttribute("data",jdbc.queryForList("SELECT * FROM income WHERE year = ? AND month = ?AND day = ?;",this.year,this.month,this.day));
				attr.addFlashAttribute("data_sum",jdbc.queryForList("SELECT sum(sal) FROM income").get(0).get("sum(sal)"));
			
			}
		}
	return "redirect:/classification";
	}
	@GetMapping("/classification")
	public String list2(Model model) {
		model.addAttribute("data",jdbc.queryForList("SELECT * FROM income"));
		model.addAttribute("data_sum",jdbc.queryForList("SELECT sum(sal) FROM income").get(0).get("sum(sal)"));
		model.addAttribute("data_day",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("day"));
		model.addAttribute("data_month",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("month"));
		model.addAttribute("data_year",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)" ).get(0).get("year"));
		return "/classification";
	}
	/////////////////////////////////////////////支出
	@PostMapping("/outClassification")
	public String Post1(String reason[], String out[],int year,int month,int day, RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("out", out);
		System.out.println(reason[i]);
			if(out[i].equals(null) != true && out[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO spending (reason,out,year,month,day) VALUES (?,?,?,?,?)",reason[i],Integer.valueOf(out[i]),year,month,day);
				attr.addFlashAttribute("data1",jdbc.queryForList("SELECT * FROM spending"));
				attr.addFlashAttribute("data_sum1",jdbc.queryForList("SELECT sum(out) FROM spending").get(0).get("sum(out)"));
				
			
			}
		}
	return "redirect:/outClassification";
	}
	@GetMapping("/outClassification")
	public String list3(Model model) {
		model.addAttribute("data1",jdbc.queryForList("SELECT * FROM spending"));
		model.addAttribute("data_sum1",jdbc.queryForList("SELECT sum(out) FROM spending").get(0).get("sum(out)"));
		model.addAttribute("data_day",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("day"));
		model.addAttribute("data_month",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("month"));
		model.addAttribute("data_year",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)" ).get(0).get("year"));
		return "/outClassification";
	}	
/////////////////////////////////////////////////貯金
	@PostMapping("/accClassification")
	public String Post2(String reason[], String acc[],int year,int month,int day, RedirectAttributes attr){
		System.out.println(reason.length);
		for(int i=0;i<reason.length;i++) {
		attr.addFlashAttribute("reason", reason);
		attr.addFlashAttribute("acc", acc);
		System.out.println(reason[i]);
			if(acc[i].equals(null) != true && acc[i].isEmpty() != true) {
				
				jdbc.update("INSERT INTO accumulation (reason,acc,year,month,day) VALUES (?,?,?,?,?)",reason[i],Integer.valueOf(acc[i]),year,month,day);
				attr.addFlashAttribute("data2",jdbc.queryForList("SELECT * FROM accumulation"));
				attr.addFlashAttribute("data_sum2",jdbc.queryForList("SELECT sum(acc) FROM accumulation").get(0).get("sum(acc)"));
						
			}
		}
	return "redirect:/accClassification";
	}
	@GetMapping("/accClassification")
	public String list4(Model model) {
		model.addAttribute("data2",jdbc.queryForList("SELECT * FROM accumulation"));
		model.addAttribute("data_sum2",jdbc.queryForList("SELECT sum(acc) FROM accumulation").get(0).get("sum(acc)"));
		model.addAttribute("data_day",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("day"));
		model.addAttribute("data_month",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)").get(0).get("month"));
		model.addAttribute("data_year",jdbc.queryForList("SELECT * FROM someday WHERE id = (SELECT MAX(id) FROM someday)" ).get(0).get("year"));

		return "/accClassification";
	}		
	


	////////////////////////////////////////////////////////////////////0619
	/*
	@PostMapping("/delete")
	public String delete(String id, RedirectAttributes attr) {
		attr.addFlashAttribute("id",id);
		jdbc.update("DELETE FROM spending WHERE id = ?",id);
		return "redirect:/outClassification";
	}*/

}

