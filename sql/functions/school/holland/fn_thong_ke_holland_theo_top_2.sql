drop function if exists school.fn_thong_ke_holland_top_2_theo_lop;

create function school.fn_thong_ke_holland_top_2_theo_lop(
    p_lop_id bigint
)
    returns setof school.vw_thong_ke_holland_theo_lop_top2
as
$$
begin
    return query select *
                 from school.vw_thong_ke_holland_theo_lop_top2
                 where lop_id = p_lop_id
                 limit 1;
end;
$$ language plpgsql;
