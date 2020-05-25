import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchStocks } from '../actions';

class StockView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchStocks(id);
  }

  render() {
    if (!this.props.stock) {
      return <div>Loading...</div>;
    }

    const { instituteName, stockNo } = this.props.stock;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{stockNo}</h5>
        <Link to="/stocks" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { stock: state.stocks[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchStocks }
)(StockView);
