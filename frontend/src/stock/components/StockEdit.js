import React from 'react';
import { connect } from 'react-redux';
import { fetchStock, editStock } from '../actions';
import StockForm from './StockForm';

class StockEdit extends React.Component {
  componentDidMount() {
    this.props.fetchStock(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editStock(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.stock) {
      return <div>Loading...</div>;
    }

    return (
      <StockForm initialValues={this.props.stock} onSubmit={this.onSubmit} title="Edit Stock" />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { stock: state.stocks[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchStock, editStock }
)(StockEdit);
