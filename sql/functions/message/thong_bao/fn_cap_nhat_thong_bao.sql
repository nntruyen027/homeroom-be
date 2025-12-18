drop function if exists message.fn_cap_nhat_thong_bao;

create or replace function message.fn_cap_nhat_thong_bao(
    p_id bigint,
    p_tieu_de text,
    p_noi_dung text
)
    returns setof message.v_thong_bao_da_xem
    language plpgsql
as
$$
begin
    update message.thong_bao
    set tieu_de  = p_tieu_de,
        noi_dung = p_noi_dung
    where id = p_id;

    return query
        select *
        from message.v_thong_bao_da_xem
        where thong_bao_id = p_id;
end;
$$;
