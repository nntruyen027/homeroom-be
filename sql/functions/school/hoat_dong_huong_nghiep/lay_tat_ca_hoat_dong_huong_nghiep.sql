DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_hoat_dong_huong_nghiep;

create function school.fn_lay_tat_ca_hoat_dong_huong_nghiep(
    p_nguoi_tao_id BIGINT,
    p_search VARCHAR(500),
    p_offset int default 0,
    p_limit int default 10
)
    returns setof school.v_hoat_dong_huong_nghiep
as
$$
begin
    return query select *
                 from school.v_hoat_dong_huong_nghiep
                 where nguoi_tao_id = p_nguoi_tao_id
                   and (p_search is null
                     or p_search = ''
                     or unaccent(lower(ten_hoat_dong)) like '%' || unaccent(lower(p_search)) || '%')
                 order by ten_hoat_dong
                 offset p_offset limit p_limit;
end;

$$ language plpgsql;
