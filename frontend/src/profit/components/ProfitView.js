import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchProfits } from '../actions';

class ProfitView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchProfits(id);
  }

  render() {
    if (!this.props.profit) {
      return <div>Loading...</div>;
    }

    const { instituteName, profitNo } = this.props.profit;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{profitNo}</h5>
        <Link to="/profits" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { profit: state.profits[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchProfits }
)(ProfitView);
