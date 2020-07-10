package dao;

import java.util.Set;

import model.AccountStatus;

public interface AccountStatusDAOInterface {
    public boolean insert(AccountStatus accountStatus);
	public boolean insertStatement(AccountStatus accountStatus);
	public AccountStatus findByFirstName(String firstName);
	public Set<AccountStatus> selectAll();
}