drop function if exists auth.fn_lay_hs_theo_id;

create or replace function auth.fn_lay_hs_theo_id(
    p_user_id bigint
)
    returns setof auth.v_hoc_sinh_full
as
$$
begin
    return query select * from auth.v_hoc_sinh_full where user_id = p_user_id limit 1;
end;
$$ language plpgsql;