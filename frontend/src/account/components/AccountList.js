import React from 'react'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { fetchAccounts } from '../actions';

class AccountList extends React.Component {
    componentDidMount() {
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
        accounts: Object.values(state.accounts)
    };
};

export default connect(mapStateToProps, { fetchAccounts })(AccountList);