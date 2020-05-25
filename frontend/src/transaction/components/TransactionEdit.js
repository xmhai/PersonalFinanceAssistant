import React from 'react';
import { connect } from 'react-redux';
import { fetchTransaction, editTransaction } from '../actions';
import TransactionForm from './TransactionForm';

class TransactionEdit extends React.Component {
  componentDidMount() {
    this.props.fetchTransaction(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editTransaction(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.transaction) {
      return <div>Loading...</div>;
    }

    return (
      <TransactionForm initialValues={this.props.transaction} onSubmit={this.onSubmit} title="Edit Transaction" />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { transaction: state.transactions[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchTransaction, editTransaction }
)(TransactionEdit);
