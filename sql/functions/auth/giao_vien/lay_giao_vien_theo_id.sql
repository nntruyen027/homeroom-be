drop function if exists auth.fn_lay_giao_vien_theo_id;

create or replace function auth.fn_lay_giao_vien_theo_id(
    p_id bigint
)
    returns setof auth.v_giao_vien_full
as
$$
begin
    return query select * from auth.v_giao_vien_full where user_id = p_id;
end;
$$ language plpgsql;