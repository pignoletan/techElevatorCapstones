select forecast
from weather
where parkcode = 'ENP'
order by fivedayforecastvalue;

begin transaction;

insert into survey_result

select count(parkcode)
from survey_result
group by parkcode
order by 1;

rollback;

select *
from park
where parkcode = 'GNP';

select *
from weather
where parkcode ='YNP'
and fivedayforecastvalue = 5;