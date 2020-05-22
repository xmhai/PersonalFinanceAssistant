import React from 'react'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { fetchAccounts } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';

class AccountList extends React.Component {
    componentDidMount() {
        this.props.fetchCurrencies();
        this.props.fetchCategories();
        this.props.fetchAccounts();
    }

    renderAdmin(account) {
        return (
            <div className="right floated content">
                <Link to={`/accounts/edit/${account.id}`} className="ui button primary">
                    Edit
                </Link>
                <Link to={`/accounts/delete/${account.id}`} className="ui button negative" >
                    Delete
                </Link>
            </div>
        );
    }

    renderList() {
        return this.props.accounts.map(account => {
            return (
                <div className="item" key={account.id}>
                    {this.renderAdmin(account)}
                    <i className="large middle aligned icon camera" />
                    <div className="content">
                        <Link to={`/accounts/${account.id}`} className="header">
                            {account.instituteName}
                        </Link>
                        <div className="description">{account.accountNo}</div>
                        <div className="description">{this.props.categories[account.categoryId].code}</div>
                    </div>
                </div>
            );
        });
    }

    renderCreate() {
        return (
            <div style={{ textAlign: 'right' }}>
                <Link to="/accounts/new" className="ui button primary">
                    Create Stream
                </Link>
            </div>
        );
    }

    render() {
        console.log(this.props);
        return (
            <div>
                <h2>Accounts</h2>
                {this.renderCreate()}
                <div className="ui celled list">{this.renderList()}</div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        accounts: Object.values(state.accounts),
        currencies: state.config.currencies,
        categories: state.config.categories
    };
};

export default connect(mapStateToProps, { fetchAccounts, fetchCurrencies, fetchCategories })(AccountList);