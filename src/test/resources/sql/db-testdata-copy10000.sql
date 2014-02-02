

insert into entries
	(title, tags, content, content_html, postdate, state)
select
	'title' || s.a, 
	x.tags || ',tag' || (s.a % 16),
	x.content, 
	x.content_html, 
	date '2010-2-1' + (s.a / 10),
	1
from
	generate_series(1,10000) as s(a),
	(select title, tags, content, content_html from entries where id = 3) x
;


insert into entries
	(title, tags, content, content_html, postdate, state)
select
	x.title || '789', 
	x.tags,
	x.content, 
	x.content_html, 
	now(),
	1
from
	(select title, tags, content, content_html from entries where id = 3) x
;




insert into comments
	(entryid, name, content, state)
select
	x.entryid, 
	x.name || s.a,
	x.content || s.a, 
	s.a % 3
from
	generate_series(1,1000) as s(a),
	(select entryid, name, content, state from comments where id = 2) x
;

