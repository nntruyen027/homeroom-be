drop function if exists message.fn_lay_thong_bao;

create or replace function message.fn_lay_thong_bao(
    p_lop_id bigint,
    p_nguoi_tao_id bigint,
    p_search varchar(500),
    p_offset int default 0,
    p_limit int default 10
)
    returns setof message.v_thong_bao_da_xem
    language plpgsql
as
$$
begin
    return query
        select *
        from message.v_thong_bao_da_xem
        where (
            p_lop_id is null
                or
            lop_id = p_lop_id)
          and (
            p_nguoi_tao_id is null
                or nguoi_tao_id = p_nguoi_tao_id
            )
          and (p_search is null
            or p_search = ''
            or unaccent(lower(tieu_de)) like '%' || unaccent(lower(p_search)) || '%')
        order by thoi_gian_tao desc
        offset p_offset limit p_limit;
end;
$$;
