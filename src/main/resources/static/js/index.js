window.onload = function() {
	var parent = document.getElementById("calendar");
	var cal = new DLYCalendar(parent);
	cal.create();
};

/***
 * カレンダークラス
 * monthは 0-11 で扱い、表示するときだけ +1 する
 */
var DLYCalendar = function(parent) {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth();

	this.nowYear = year;			// 現在の日付
	this.nowMonth = month;

	this.year = year;				// 表示する日付
	this.month = month;

	this.days = new Array(7*6);		// 日付データ　：　7 (day of the week) x 6 (row)
	this.clss = new Array(7*6);		// 日付に付加するクラス名

	this.parent = parent;			// カレンダーの親要素
	this.id = "calendar_table";		// テーブル要素のID
	this.title = "calender_title";	// 年月タイトルのID
};

/***
 * カレンダー作成
 */
DLYCalendar.prototype.create = function() {
	// 日付データ作成
	this.cre_days();

	// ヘッダ部作成
	var table = this.cre_header();

	// カレンダー本体作成、および親要素へ追加
	this.cre_dom(table);

	// clickイベント監視
	table.addEventListener('click', function(e) {
		var td = e.target;
		if (td.dataset.daysIdx) {
			var idx = Number(td.dataset.daysIdx);
			var elt = document.getElementById("selmonth");
			var elt1 = document.getElementById("selday");
			elt.textContent = this.month+1;			
			elt1.textContent = this.days[idx];
			
			this.day[idx]=document.getElementById("dayin").value ///now
	
		}
	}	
	.bind(this));
};
/*


/***
 * カレンダー更新
 */
DLYCalendar.prototype.update = function() {
	// 日付データ作成
	this.cre_days();

	// 年月タイトル更新
	var title = document.getElementById(this.title);
	title.textContent = this.year + " / " + (this.month +1);

	// 月移動したときの日付更新
	var elt = document.getElementById(this.id);
	var td = elt.getElementsByTagName("td");
	for (var i=0; i<7*6; i++) {
		td[i].textContent = this.days[i];
		td[i].className = this.clss[i];
	}
};

/***
 * ヘッダ部作成
 */
DLYCalendar.prototype.cre_header = function() {
	var table, tr, th, text;
	var week = ["日","月","火","水","木","金","土"];

	table = document.createElement("table");
	table.id = this.id;

	// 一行目（"<", "yyyy/m/d", ">"）
	tr = document.createElement("tr");

	th = document.createElement("th");
	th.className = "cal_prev";
	th.onclick = function() {this.showprev();}.bind(this);
	text = document.createTextNode("<");
	th.appendChild(text);
	tr.appendChild(th)

	th = document.createElement("th");
	th.id = this.title;
	th.textContent = this.year + " / " + (this.month +1);
	th.setAttribute("colspan", "5");
	th.onclick = function() {this.show();}.bind(this);
	tr.appendChild(th);

	th = document.createElement("th");
	th.className = "cal_next";
	th.onclick = function() {this.shownext();}.bind(this);//移動する
	text = document.createTextNode(">");
	th.appendChild(text);
	tr.appendChild(th);

	table.appendChild(tr);

	// 二行目（曜日）
	tr = document.createElement("tr");
	for(var i=0; i<7; i++) {
		th = document.createElement("th");
		th.textContent = week[i];
		tr.appendChild(th);
	}

	table.appendChild(tr);

	return table;
};

/***
 * 作成するカレンダーの年月（this.year, this.month）から
 * 日付データとクラス名（this.days[], this.clss[]）を作成する
 */
DLYCalendar.prototype.cre_days = function() {
	// 前の月の最終日　：　「0日」は、「１日」の前の日、つまり前の月の最終日を表す
	var prevEndDate = new Date(this.year, this.month, 0);
	var prevEndDay = prevEndDate.getDate();				// 日にち
	var prevEndWeek = prevEndDate.getDay();				// 曜日: 0-6

	// 指定月の最終日
	var nowEndDate = new Date(this.year,　this.month　+1, 0);
	var nowEndDay = nowEndDate.getDate();				// 日にち
	var nowEndWeek = nowEndDate.getDay();				// 曜日: 0-6

	var num, day, i;

	// 前の月の日にちを格納
	num = (prevEndWeek +1) % 7;
	day = prevEndDay - prevEndWeek; 	// 日曜日（this.days[0]）の日付をセット
	for(i=0; i<num; i++) {
		this.days[i] =  day++;
		this.clss[i] = "outrange";
	}

	// 当月の日にちを格納
	num = i + nowEndDay;
	day = 1;
	for(; i<num; i++) {
		this.days[i] =  day++;
		this.clss[i] = "inrange";
	}

	// 次の月の日にちを格納
	num = 7 * 6;
	day = 1;
	for(; i<num; i++) {
		this.days[i] =  day++;
		this.clss[i] = "outrange";
	}
};

/***
 * カレンダー本体作成
 */
DLYCalendar.prototype.cre_dom = function(table) {
	for(var y=0; y<6; y++) {	// row of calendar
		var tr = document.createElement("tr");
		for(var i=0; i<7; i++) {		// day of the week
			var td = document.createElement("td");
			td.textContent = this.days[i + y*7];
			td.className = this.clss[i + y*7];
			td.dataset.daysIdx = i + y*7;
			tr.appendChild(td);
		}
		table.appendChild(tr);
	}

	var tbl = document.getElementById(this.id);
	if (tbl) {
		this.parent.removeChild(tbl);
	}

	// 作成したカレンダーのテーブル要素を追加する
	this.parent.appendChild(table);
};

/***
 * ひと月前のカレンダーを表示
 */
DLYCalendar.prototype.showprev = function() {
	if (this.month === 0) {
		this.year--;
		this.month = 11;
	} else {
		this.month--;
	}
	this.update();
};

/***
 * ひと月後のカレンダーを表示
 */
DLYCalendar.prototype.shownext = function() {
	if (this.month === 11) {
		this.year++;
		this.month = 0;
	} else {
		this.month++;
	}
	this.update();
};

/***
 * 指定した年月のカレンダーを表示
 * 引数を指定しない場合は、カレンダー生成時の年月を表示する
 */
DLYCalendar.prototype.show = function(year, month) {
	this.year = year || this.nowYear;
	this.month = month || this.nowMonth;
	this.update();
};