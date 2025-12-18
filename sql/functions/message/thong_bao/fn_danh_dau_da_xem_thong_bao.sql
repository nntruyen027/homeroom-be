drop function if exists message.fn_danh_dau_da_xem_thong_bao;

create or replace function message.fn_danh_dau_da_xem_thong_bao(
    p_thong_bao_id bigint,
    p_user_id bigint
)
    returns void
    language plpgsql
as
$$
begin
    insert into message.thong_bao_hoc_sinh(thong_bao_id,
                                           user_id,
                                           da_xem,
                                           thoi_gian_xem)
    values (p_thong_bao_id,
            p_user_id,
            true,
            now())
    on conflict (thong_bao_id, user_id)
        do update
        set da_xem        = true,
            thoi_gian_xem = now();
end;
$$;
