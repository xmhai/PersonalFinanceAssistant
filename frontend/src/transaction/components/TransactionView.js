import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchTransactions } from '../actions';

class TransactionView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchTransactions(id);
  }

  render() {
    if (!this.props.transaction) {
      return <div>Loading...</div>;
    }

    const { instituteName, transactionNo } = this.props.transaction;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{transactionNo}</h5>
        <Link to="/transactions" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { transaction: state.transactions[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchTransactions }
)(TransactionView);
