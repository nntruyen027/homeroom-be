drop function if exists message.fn_xoa_thong_bao;

create or replace function message.fn_xoa_thong_bao(
    p_id bigint
)
    returns void
    language plpgsql
as
$$
begin
    delete
    from message.thong_bao
    where id = p_id;
end;
$$;
