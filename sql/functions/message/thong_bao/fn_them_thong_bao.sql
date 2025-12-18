drop function if exists message.fn_them_thong_bao;

create or replace function message.fn_them_thong_bao(
    p_tieu_de text,
    p_noi_dung text,
    p_lop_id bigint,
    p_nguoi_tao_id bigint
)
    returns setof message.v_thong_bao_da_xem
    language plpgsql
as
$$
declare
    v_id bigint;
begin
    insert into message.thong_bao (tieu_de,
                                   noi_dung,
                                   lop_id,
                                   nguoi_tao_id)
    values (p_tieu_de,
            p_noi_dung,
            p_lop_id,
            p_nguoi_tao_id)
    returning id into v_id;

    return query
        select *
        from message.v_thong_bao_da_xem
        where thong_bao_id = v_id;
end;
$$;
