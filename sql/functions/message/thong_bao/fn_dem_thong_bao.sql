drop function if exists message.fn_dem_thong_bao;

create or replace function message.fn_dem_thong_bao(
    p_lop_id bigint,
    p_nguoi_tao_id bigint,
    p_search varchar(500)
)
    returns bigint
    language plpgsql
as
$$
declare
    v_count bigint;
begin
    select count(*)
    into v_count
    from message.thong_bao
    where lop_id = p_lop_id
      and (
        p_lop_id is null
            or
        lop_id = p_lop_id)
      and (
        p_nguoi_tao_id is null
            or nguoi_tao_id = p_nguoi_tao_id
        )
      and (p_search is null
        or p_search = ''
        or unaccent(lower(tieu_de)) like '%' || unaccent(lower(p_search)) || '%');

    return v_count;
end;
$$;
